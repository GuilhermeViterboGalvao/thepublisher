(function() {
        // Load plugin specific language pack
        tinymce.PluginManager.requireLangPack('photogallery');

        tinymce.create('tinymce.plugins.PhotoGalleryPlugin', {
            /**
             * Initializes the plugin, this will be executed after the plugin has been created.
             * This call is done before the editor instance has finished it's initialization so use the onInit event
             * of the editor instance to intercept that event.
             *
             * @param {tinymce.Editor} ed Editor instance that the plugin is initialized in.
             * @param {string} url Absolute URL to where the plugin is located.
             */
            init : function(ed, url) {
                // Register the command so that it can be invoked by using tinyMCE.activeEditor.execCommand('mceExample');
                ed.addCommand('mceExample', function() {
                    ed.windowManager.open({
                            file    : url + '/dialog.htm',
                            width   : 588 + ed.getLang('photogallery.delta_width', 0),
                            height  : 314 + ed.getLang('photogallery.delta_height', 0),
                            inline  : 1
                    }, {
                            plugin_url : url, // Plugin absolute URL
                    });
                });
                // Register example button
                ed.addButton('photogallery', {
                    title : 'photogallery.desc',
                    cmd   : 'mceExample',
                    image : url + '/img/example.gif'
                });
            },
            /**
             * Returns information about the plugin as a name/value array.
             * The current keys are longname, author, authorurl, infourl and version.
             *
             * @return {Object} Name/value array containing information about the plugin.
             */
            getInfo : function() {
                return {
                    longname  : 'PhotoGallery plugin',
                    author    : 'The Publisher',
                    authorurl : 'http://www.tatame.com.br/',
                    infourl   : 'http://www.tatame.com.br/',
                    version   : "1.0"
                };
            }
        });

        // Register plugin
        tinymce.PluginManager.add('photogallery', tinymce.plugins.PhotoGalleryPlugin);
})();