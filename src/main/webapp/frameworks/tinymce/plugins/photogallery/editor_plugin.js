(function(){tinymce.PluginManager.requireLangPack('photogallery');tinymce.create('tinymce.plugins.PhotoGalleryPlugin',{init:function(ed,url){ed.addCommand('mceExample',function(){ed.windowManager.open({file:url+'/dialog.htm',width:588+ed.getLang('photogallery.delta_width',0),height:314+ed.getLang('photogallery.delta_height',0),inline:1},{plugin_url:url,})});ed.addButton('photogallery',{title:'photogallery.desc',cmd:'mceExample',image:url+'/img/example.gif'})},getInfo:function(){return{longname:'PhotoGallery plugin',author:'The Publisher',authorurl:'http://www.tatame.com.br/',infourl:'http://www.tatame.com.br/',version:"1.0"}}});tinymce.PluginManager.add('photogallery',tinymce.plugins.PhotoGalleryPlugin)})();