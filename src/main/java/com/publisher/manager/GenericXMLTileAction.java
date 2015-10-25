package com.publisher.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.opensymphony.xwork2.ActionSupport;
import com.publisher.entity.Account;
import com.publisher.utils.DOMUtil;
import com.publisher.utils.TileRepository;

public class GenericXMLTileAction extends ActionSupport implements ParameterAware, ServletRequestAware, AccountAware {

	private static final long serialVersionUID = -6996189175468767243L;

	private Account account;
	
	@Override
	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public Account getAccount() {
		return account;
	}
	
	private HttpServletRequest request;

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	private Map<String, String[]> parameters;

	@Override
	public void setParameters(Map<String, String[]> parameters) {
		this.parameters = parameters;
	}
	
	public String edit() {
		populateEntries();
		populateExtraEntries();
		return SUCCESS;
	}

	private void populateAttributes(NamedNodeMap map) {
		if (attributes == null) {
			attributes = new HashMap<String,String>(map.getLength());
			for(int i = 0; i < map.getLength(); i++) {
				Node node = map.item(i);
				attributes.put(node.getNodeName(), node.getNodeValue());
			}
		}
	}
	
	public String update() {
		Map<String, String[]> map =  new HashMap<String, String[]>();
		for(Entry<String,String[]> entry : parameters.entrySet()) {
			if (!("path".equals(entry.getKey()) || entry.getKey().startsWith("action:") || "version".equals(entry.getKey())))
				map.put(entry.getKey(), entry.getValue());
		}
		Document doc = null;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File((String)ServletActionContext.getServletContext().getAttribute("data-folder"), path + ".xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Element root = doc.getDocumentElement();
		populateAttributes(root.getAttributes());
		try { 
			max = Integer.parseInt(attributes.get("max")); 
		} catch (NumberFormatException e) {}
		int currentVersion = 0;
		try {
			currentVersion = Integer.parseInt(attributes.get("version")); 
		} catch (NumberFormatException e) {}
		if (currentVersion == version) {
			Node node = null;
			while ((node = DOMUtil.getChild(root, Node.ELEMENT_NODE)) != null) {
				root.removeChild(node);
			}
			root.setTextContent("");
			if (map.size() > 0) {
				Element[] elements = null;
				for (Entry<String,String[]> entry:map.entrySet()) {
					String[] s = entry.getValue();
					if (elements == null) 
						elements = new Element[s.length];
					for (int i = 0; i < s.length; i++) {
						if (elements[i] == null) {
							elements[i] = doc.createElement("item");
						}
						if (s[i].length() > 0) {
							Element property = doc.createElement(entry.getKey());
							property.setTextContent(s[i]);
							elements[i].appendChild(property);
						}
					}
					int count = 0;
					for (Element e : elements) {
						if (max != 0 && count >= max) {
							break;
						}
						if (e.hasChildNodes()) {
							root.appendChild(e);
							count++;
						}
					}
				}
			}
			root.setAttribute("version", "" + System.currentTimeMillis());
			try {
				StringWriter writer = new StringWriter();
				DOMSource domSource = new DOMSource(doc);
				StreamResult result = new StreamResult(writer);
				TransformerFactory tf = TransformerFactory.newInstance();
				tf.setAttribute("indent-number", "2");
				Transformer transformer = tf.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.transform(domSource, result);
				OutputStreamWriter out = null;
				try {
					out = new OutputStreamWriter(new FileOutputStream(new File((String)ServletActionContext.getServletContext().getAttribute("data-folder"), path + ".xml")), "UTF-8");
					out.write(writer.toString());
				} catch (Exception e) {
					message = e.getMessage();
				} finally {
					try {
						out.close();
					} catch (Exception e2) {}
				}
			} catch (Exception e) {
				message = e.getMessage();
			} finally {
				TileRepository.getInstance().clear(path);
			}			
		} else {
			message = "Falha ao Atualizar.\nConteúdo alterado por outro usuário.\nTente novamente.";
		}
		return edit();
	}

	private void populateEntries() {
		Document doc = null;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File((String)ServletActionContext.getServletContext().getAttribute("data-folder"), path + ".xml"));
		} catch (Exception e) {
			message = e.getMessage();
			return;
		}
		entries = new ArrayList<Map<String,String>>();
		Element root = doc.getDocumentElement();
		populateAttributes(root.getAttributes());
		form = attributes.get("form");
		name = attributes.get("name");
		try { 
			version = Integer.parseInt(attributes.get("version")); 
		} catch (NumberFormatException e) {}
		try { 
			min = Integer.parseInt(attributes.get("min")); 
		} catch (NumberFormatException e) {}
		try { 
			max = Integer.parseInt(attributes.get("max")); 
		} catch (NumberFormatException e) {}
		NodeList list = root.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if (node instanceof Element) {
				Element e = (Element) node;
				NodeList list2 = e.getChildNodes();
				Map<String,String> map = new HashMap<String,String>();
				for (int j = 0; j < list2.getLength(); j++) {
					Node node2 = list2.item(j);
					if (node2 instanceof Element) {
						Element e2 = (Element) node2;
						map.put(e2.getTagName(), e2.getTextContent());
					}
				}
				entries.add(map);
			}
		}
	}
	
	protected void populateExtraEntries() {
		if (entries.size() < min) {
			for (int i = 0; i < (min - entries.size()); i++) {
				entries.add(new HashMap<String, String>(0));
			}
		} else if (entries.size() < max || max == 0) {
			entries.add(0,new HashMap<String, String>(0));
		}
	}
	
	//Action properties
	
	public String getSessionId(){
		return request.getSession().getId();
	}
	
	//POJO
	
	protected List<Map<String,String>> entries;
	
	protected Map<String,String> attributes;
	
	private String message;
	
	private int version;
	
	private String form;
	
	private String name;
	
	private String path;
	
	private int max;
	
	private int min;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}
}