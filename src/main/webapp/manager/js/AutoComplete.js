(function(window, undefined) {
	var
	//>>>>>>https://github.com/dperini/ContentLoaded/blob/master/src/contentloaded.js
	contentLoaded = function(win, fn) {
		var done = false, top = true,

		doc = win.document, root = doc.documentElement,

		add = doc.addEventListener ? 'addEventListener' : 'attachEvent', rem = doc.addEventListener ? 'removeEventListener'	: 'detachEvent', pre = doc.addEventListener ? '' : 'on',

				init = function(e) {
					if (e.type == 'readystatechange' && doc.readyState != 'complete')
						return;
					(e.type == 'load' ? win : doc)[rem](pre + e.type, init, false);
					if (!done && (done = true))
						fn.call(win, e.type || e);
				},

				poll = function() {
					try {
						root.doScroll('left');
					} catch (e) {
						setTimeout(poll, 50);
						return;
					}
					init('poll');
				};

				if (doc.readyState == 'complete')
					fn.call(win, 'lazy');
				else {
					if (doc.createEventObject && root.doScroll) {
						try {
							top = !win.frameElement;
						} catch (e) {
						}
						if (top)
							poll();
					}
					doc[add](pre + 'DOMContentLoaded', init, false);
					doc[add](pre + 'readystatechange', init, false);
					win[add](pre + 'load', init, false);
				}

	},
	injectCss = function (css) {
	    var style = document.createElement('style');
	    style.setAttribute('type', 'text/css');
	    css = css.replace(/\}/g, "}\n");
	    if (style.styleSheet) {
	        style.styleSheet.cssText = css;
	    } else {
	        style.appendChild(document.createTextNode(css));
	    }
	    var entry = document.getElementsByTagName('script')[0];
	    entry.parentNode.insertBefore(style, entry);
	},
	offset = function(elem) {
		var box, docElem, body, win, clientTop, clientLeft, scrollTop, scrollLeft, top, left,
		doc = elem && elem.ownerDocument;
		docElem = doc.documentElement;
		box = elem.getBoundingClientRect();
		body = doc.body;
		win = window;
		clientTop  = docElem.clientTop  || body.clientTop  || 0;
		clientLeft = docElem.clientLeft || body.clientLeft || 0;
		scrollTop  = win.pageYOffset || docElem.scrollTop;
		scrollLeft = win.pageXOffset || docElem.scrollLeft;
		top  = box.top  + scrollTop  - clientTop;
		left = box.left + scrollLeft - clientLeft;
		return { top: top, left: left };			
	},
	addEvent = function(event, target, method) {
		if (target.addEventListener) {
			target.addEventListener(event, method, false);
		} else if (target.attachEvent) {
			target.attachEvent("on" + event, method);
		}
	},
	jsonp = function(url) {
		var script = document.createElement('script');
		script.type = 'text/javascript';
		script.async = true;
		script.src = url;
		window.document.body.appendChild(script);
	},
	trim = function (s) {
		return s.replace(/^\s*/, "").replace(/\s*$/, "");
	},
	AutoComplete = function(id, options) {
		var ac = new AutoComplete.fn.init(id, options);
		return ac;
	};

	AutoComplete.fn = AutoComplete.prototype = {
			constructor: AutoComplete,
			type: "autocomplete",
			ready : function() {
				injectCss(this.css());
				this.menu=window.document.createElement("ul");
				this.menu.className=this.type;
				this.menu.style.display="none";
				var element = document.getElementById(this.name);
				if (element==null) element = document.getElementById(this.options.target);
				if (element!=null) this.bind(element);
			},
			bind : function(element) {
				if (this.options.initialUrl!=null) {
					this.load(null,"update");
				}
				if (this.options.menu==null)
					window.document.body.appendChild(this.menu);
				else 
					window.document.getElementById(this.options.menu).appendChild(this.menu);
				this.element=element;
				addEvent("keydown",this.element,(function(t){return function(e){t.nav(e);};})(this));
				addEvent("keyup",this.element,(function(t){return function(){t.change();};})(this));
				if (this.options.initial) this.update(this.options.initial);
			},
			init : function(name, options) {
				this.options = options;
				this.name=name;
				this.minLength = 3;
				if (options.minLength!=null) this.minLength = options.minLength;
				this.showToolTip = true;
				if (options.showToolTip!=null) this.showToolTip=options.showToolTip=="true";				
				this.delay = 200;
				if (options.delay!=null) this.delay = options.delay;
				this.pagesize = 60;
				if (options.pageSize!=null) this.pagesize = options.pageSize;
				this.page=1;
				window[this.name] = this;
				if (this.options.click) this._click=this.options.click;
				if (this.options.render) this._render=this.options.render;
				contentLoaded(window,(function(t){return function(){t.ready();};})(this));
			},
			css: function() { return "ul."+this.type+" { margin: 0; padding: 0; border-top: 1px solid gray; border-bottom: 1px solid gray; max-height: 240px; overflow-y: scroll; z-index: 1;}" +
				"ul."+this.type+">li.select { background-color: yellow;}"+
				"ul."+this.type+">li:last-child { border-bottom: 0;}"+
				"ul."+this.type+">li {cursor: pointer;list-style: none;padding: 4px;margin: 0;border-right: 1px solid gray;border-left: 1px solid gray;border-bottom: 1px solid gray;background-color: white;}"+
				"ul."+this.type+">li.more:before { content: 'Carregar mais'}"+
				"ul."+this.type+">li.more { clear: both; float: none; text-align: center; text-transform: uppercase}" + 
				"ul."+this.type+">li.more:hover { background-color: yellow }";},
			move: function(d) {
				var itens = this.menu.getElementsByTagName("li");
				var c=-1;
				var count = 0;
				for (var i=0; i<itens.length; i++) {
					if (itens[i].className!='next'&&itens[i].className!='previous') {
						itens[i].className="";
						if (itens[i]==this.current) c=i;
						count++;
					}
				}
				c = c+d;
				if (c<0) c=0;
				if (c>=count) c = count-1;
				if (itens[c]==undefined) return;
				itens[c].className="select";
				this.current = itens[c];
				if (this.current.offsetTop>this.current.offsetParent.offsetHeight/2) {
					var scroll = (c-3)*this.current.offsetHeight;
					if (scroll<0) scroll = 0;
					this.menu.scrollTop = scroll;
				}
			},
			nav: function (event) {
				switch( event.keyCode ) {
				case 38:
					this.move(-1);
					event.preventDefault();
					break;
				case 40:
					this.move(1);
					event.preventDefault();
					break;
				case 13:
					if (this.current!=null)
						this.current.click();
					event.preventDefault();
				}
			},
			change : function() {	
				var search = trim(this.element.value);
				if (search.length<this.minLength) search = null;
				if (search==this.lastSearch) return;
				this.resetTimer();
				if (search!=null&&search.length>=this.minLength)
					this.timer = setTimeout((function(t){return function(){t.load(search,"update");t.resetTimer();};})(this),this.delay);
				else {
					if (this.options.initialUrl==null) {
						this.menu.style.display="none";
					} else {
						this.timer = setTimeout((function(t){return function(){t.load(search,"update");t.resetTimer();};})(this),this.delay);
					}
				}
				this.page=1;
				this.lastSearch = search;
			},
			resetTimer: function() {
				if (this.timer!=null) {
					clearTimeout(this.timer);
					timer = null;
				}
			},
			load : function(search,method) {
				var url;
				if (search==null)
					url = this.options.initialUrl;
				else {
					url = this.options.url;
					url = url + (url.indexOf('?')>0?'&':'?');
					url = url + "term="+search;
				}
				url = url + (url.indexOf('?')>0?'&':'?');
				if (this.options.isEvent && this.options.isEvent != undefined) {
					url = url + "&isEvent=true";
				}
				url = url + "&pagesize="+this.pagesize+"&page="+this.page+"&callback="+this.name+"."+method;
				jsonp(url);
			},
			more : function() {
				this.page=this.page+1;
				this.load(this.lastSearch,"add");
			},
			add :function(data) {
				for(var i=0;i<data.length;i++) {
					var item = this._render(data[i]);
					addEvent("click",item,(function(t,d){return function(){t.click(d);};})(this,data[i]));
					addEvent("mouseover",item,(function(t,i){return function(){t.current=i;i.className="select";};})(this,item));
					addEvent("mouseout",item,(function(i){return function(){i.className="";};})(item));
					this.menu.appendChild(item);
					if (i>=this.pagesize) break;
				}
				if (data.length>0&&data.length==this.pagesize) {
						var next = window.document.createElement("li");
						next.className="more";
						addEvent("click",next,(function(t){return function(){this.parentNode.removeChild(this);t.more();};})(this));
						this.menu.appendChild(next);
					
				}
			},
			update : function(data) {	
				while (this.menu.childNodes.length > 0) this.menu.removeChild(this.menu.firstChild);
				if (this.options.menu==null) {
					var os = offset(this.element);
					this.menu.style.top=(os.top+this.element.offsetHeight)+"px";
					this.menu.style.left=os.left+"px";
					this.menu.style.position="absolute";
					this.menu.style.zIndex = 10000;
				}
				this.add(data);
				this.menu.style.display="block";
			},
			click : function(obj) {
				this.menu.style.display="none";
				this.resetTimer();
				this._click(obj);
			},
			_click : function(obj) {
				this.element.value=obj.label;
			},
			_render : function(obj) {
				var li =  window.document.createElement("li");
				li.appendChild(window.document.createTextNode(obj.label));
				return li;
			}
	};
	AutoComplete.fn.init.prototype = AutoComplete.fn;
	window.AutoComplete = AutoComplete;
	
	var PhotoSelector = function(id, options) {
		
		var ps = new AutoComplete.fn.init(id, options);

		var addToolTip = function(e,toolbox) {
			toolbox.innerHTML=e.target.alt;
		};
		var removeToolTip = function(e,toolbox) {
			toolbox.innerHTML="";
		};
		
		ps.getImageURL = function(obj) {
			var url = obj.label;
			if (this.options.imagePattern!=null) {
				url = this.options.imagePattern;
				url = url.replace("[label]",obj.label);
				url = url.replace("[value]",obj.value);
			}
			return url;
			
		};
		ps.bind = function(e){
			this.constructor.prototype.bind.call(this,e);
			if (this.showToolTip) {
				if (this.toolbox==null) {
					this.toolbox = window.document.createElement("div");
					this.toolbox.className="toolbox";
				}
				this.menu.parentNode.insertBefore(this.toolbox,this.menu);
			}
		};
		ps._render = function(obj) {
			var li = document.createElement('li');
			var img = document.createElement('img');
			var url = this.getImageURL(obj);
			if (this.options.loadingImage!=null) {
				img.src=this.options.loadingImage;
			}
			if (url!=obj.label) {
				img.alt = obj.label;
				if (this.toolbox!=null) {
					var toolbox = this.toolbox;
					addEvent('mouseover', img, function (e) { addToolTip(e,toolbox); });
					addEvent('mouseout', img,  function (e) { removeToolTip(e,toolbox); });
				}
			}
			img.src=url;
			li.appendChild(img);
			return li;
		};
		ps.type="photoselector";
		ps.css = function() { return "div."+this.type+" { width: 400px; background-color: white; border: 1px solid black; position: absolute; z-index: 30; }" +
			"div."+this.type+" input { width: 316px; margin: 4px; }" +
			"img.photoselector { border: 1px solid black; cursor: pointer }" +
			"div."+this.type+">div.toolbox { background-color: #ddd; border-top: 1px solid gray; padding: 4px; height: 22px; font-size: 10px; overflow: hidden}"+
			"ul."+this.type+" { margin: 0; padding: 0; border-top: 1px solid gray; max-height: 240px; overflow-y: scroll;}" +
			"ul."+this.type+">li.select { background-color: yellow;}"+
			"ul."+this.type+">li {cursor: pointer;list-style: none;padding: 4px;background-color: white; float: left;}"+
			"ul."+this.type+">li>img {display: block; width: 100px; height: 70px; border: 1px solid black; }"+
			"ul."+this.type+">li.more:before { content: 'Carregar mais'}"+
			"ul."+this.type+">li.more { clear: both; float: none; text-align: center; border-top: 1px solid black; text-transform: uppercase; padding: 4px;}"+
			"ul."+this.type+">li.more:hover { background-color: yellow }";};
		return ps;
	};
	window.PhotoSelector = PhotoSelector;
	
	if (window['StrutsAC']!=undefined) {
		return;
	}
	
	var d = window.document;
	
	var StrutsAC = {
			search: function(id) {
				var span = d.getElementById(id+"_ACS");
				while (span.childNodes.length > 0) span.removeChild(span.firstChild);
				var textfield = d.createElement("input");
				textfield.type="text";
				textfield.id=id+"_ACSInput";
				span.appendChild(textfield);
				textfield.focus();
				window[id+"_ACSEngine"].bind(textfield);
			},
			update: function(id,obj,t,evaluator) {
				var span = d.getElementById(id+"_ACS");
				while (span.childNodes.length > 0) span.removeChild(span.firstChild);
				var a = d.createElement("a");
				a.href="javascript:StrutsAC.search('"+id+"')";
				a.appendChild(d.createTextNode(obj.label));
				span.appendChild(a);
				d.getElementById(id).value = ((evaluator != undefined) ? evaluator(obj) : obj.value);
			}
	};
	
	window['StrutsAC'] = StrutsAC;
	
	var StrutsPS = {
			search: function(id) {
				var img = d.getElementById(id+"_ACS");
				var div = d.createElement("div");
				var os = offset(img);
				div.style.top=(os.top)+"px";
				div.style.left=(os.left)+"px";
				div.style.position = "absolute";
				div.className="photoselector";
				div.id = id+"_ACSBox";
				var imgClose = d.createElement("img");
				imgClose.style.marginLeft="375px";
				imgClose.style.position="absolute";
				imgClose.src="/manager/img/close.gif";
				imgClose.onclick = function() {
					div.parentNode.removeChild(div);
				};
				div.appendChild(imgClose);
				var textfield = d.createElement("input");
				textfield.type="text";
				textfield.style.marginLeft="13px";
				textfield.style.top="3px";
				textfield.style.width="347px";
				textfield.id=id+"_ACSInput";
				div.appendChild(textfield);
				d.body.appendChild(div);
				textfield.focus();
				window[id+"_ACSEngine"].bind(textfield);
			},
			update: function(id,obj,t,evaluator) {
				var img = d.getElementById(id+"_ACS");
				d.body.removeChild(document.getElementById(id+"_ACSBox"));
				var value = obj.value;
				if (evaluator!=undefined) value = evaluator(obj);
				d.getElementById(id).value=value;
				img.src=t.getImageURL(obj);
			}
	};
	
	window['StrutsPS'] = StrutsPS;	
	
})(window);