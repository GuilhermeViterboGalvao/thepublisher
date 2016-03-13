package br.com.clubetatame.manager;

public class ArticleAction extends com.publisher.manager.ArticleAction {

	private static final long serialVersionUID = 4125239732671134636L;
	
	public String getInput() {
		if (type != null && !type.equals("")) {
			return "manager-clube-article-" + type;
		}
		return "manager-clube-article-input";
	}
}