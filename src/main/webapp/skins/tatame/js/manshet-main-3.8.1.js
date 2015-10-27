jQuery(document).ready(function($)
{
    'use strict';
    bd_article_blog(); // bd_article_blog
    bd_criteria_percent(); // bd_criteria_percent
    bd_flexslider(); // bd_flexslider
    bd_tabs(); // bd_tabs
    bd_preventDefault(); // bd_preventDefault
    bd_breaking_news_in_pic(); //bd_breaking_news_in_pic
    bd_home_icon(); //bd_home_icon
 //   bd_lightbox(); //bd_lightbox
    bd_modernTicker(); //bd_modernTicker
    initTabGroup(); //initTabGroup
    bd_toggle(); //bd_toggle
    bd_menus(); //bd_menus
    bd_forms(); //bd_forms
    bd_fitVids(); //bd_fitVids
    bd_imgHover(); //bd_imgHover
    bd_goTop(); //bd_goTop
    bd_tipsy(); //bd_tipsy

});

/* bd_article_blog */
function bd_article_blog()
{
    'use strict';
    jQuery('article.blog-two:odd, .cat-box-2col.blog-two:odd ').css('margin-right', '0px');
    jQuery('article.blog-two:odd, .cat-box-2col.blog-two:odd ').after('<div class="clear clearfix"></div>');
    jQuery('#breaking-news-in-pic div.post-image a img, .home-scroll div.post-image a img').after('<span class="over"><i class="icon-play"></i></span>');
}

/* bd_criteria_percent */
function bd_criteria_percent()
{
    'use strict';
    setTimeout(function()
    {
        jQuery('.bd-criteria-percent .bd-criteria-percentage').each(function()
        {
            var me = jQuery(this);
            var perc = me.attr("data-percentage");
            var current_perc = 0;
            var progress = setInterval(function()
            {
                if (current_perc>=perc)
                {
                    clearInterval(progress);
                } else {
                    current_perc +=1;
                    me.css('width', (current_perc)+'%');
                }
                me.text((current_perc)+'%');
            }, 10);
        });
    },10);
}

/* bd_flexslider */
function bd_flexslider()
{
    'use strict';
    jQuery('#slider.flexslider ul.slides li, #slider.flexslider .flex-direction-nav li a').hover(function ()
    {
        jQuery('#slider.flexslider .flex-direction-nav li .flex-next').css('right', '0px');
        jQuery('#slider.flexslider .flex-direction-nav li .flex-prev').css('left', '0px');
    }, function ()
    {
        jQuery('#slider.flexslider .flex-direction-nav li .flex-next').css('right', '-50px');
        jQuery('#slider.flexslider .flex-direction-nav li .flex-prev').css('left', '-50px');
    });
}

/* bd_tabs */
function bd_tabs()
{
    'use strict';
    jQuery(".tab_container").hide();
    jQuery("ul.tabs_nav li:first").addClass("active").show();
    jQuery(".tab_container:first").show();
    jQuery("ul.tabs_nav li").click(function() {
        jQuery("ul.tabs_nav li").removeClass("active");
        jQuery(this).addClass("active");
        jQuery(".tab_container").hide();
        var activeTab = jQuery(this).find("a").attr("href");
        jQuery(activeTab).fadeIn();
        return false;
    });
}

/* bd_preventDefault */
function bd_preventDefault()
{
    'use strict';
    jQuery(".box-title-more a.prev,.box-title-more a.nxt , ul.tabs_nav li a").click(function(event)
    {
        event.preventDefault(event);
    });
}

/* bd_breaking_news_in_pic */
function bd_breaking_news_in_pic()
{
    'use strict';
    jQuery("#breaking-news-in-pic .post-warpper .post-item, .home-scroll .post-warpper .post-item").hover(function(event){
            {
                jQuery(this).find('.post-caption').stop().animate({opacity: 1, bottom: "0px"}, {duration: 200});
            }
        },
        function()
        {
            jQuery(this).find('.post-caption').stop().animate({opacity: 1, bottom: "-37px"}, {duration: 200});
        }
    );
}

/* bd_home_icon */
function bd_home_icon()
{
    'use strict';
    jQuery('#nav li.home a').html('<i class="icon-home"></i>'); //Added html - append - before - after
}

/* bd_lightbox */
function bd_lightbox()
{
   // 'use strict';
   // jQuery('.lightbox').lightbox();
}

/* bd_modernTicker */
function bd_modernTicker()
{
    'use strict';
    jQuery("#breaking-news").modernTicker({
        effect: "type",
        displayTime: 4000,
        transitionTime: 300,
        autoplay: true
    });
}

/* bd_toggle */
function bd_toggle()
{
    'use strict';
    jQuery('div.toggle h4').click(function () {
        var text = jQuery(this).siblings('div.panel');
        if (text.is(':hidden')) {
            text.slideDown('200');
            jQuery(this).siblings('span').html('-');
        } else {
            text.slideUp('200');
            jQuery(this).siblings('span').html('+');
        }
    });
}

/* bd_menus */
function bd_menus()
{
    'use strict';
    jQuery(".top-menu li, nav#nav li").each(function()
    {
        var $sublist = jQuery(this).find('ul:first');
        jQuery(this).hover(function()
            {
                $sublist.stop().css({overflow:"hidden", height:"auto", display:"none"}).slideDown(100, function()
                {
                    jQuery(this).css({overflow:"visible", height:"auto"});
                });
            },

            function()
            {
                $sublist.stop().slideUp(100, function()
                {
                    jQuery(this).css({overflow:"hidden", display:"none"});
                });
            });
    });

    jQuery("<select />").appendTo(".top-menu, nav");
    jQuery("<option />",
        {
            "selected": "selected",
            "value": "",
            "text": "Go to ..."
        }).appendTo(".top-menu select, nav select");

    jQuery(".top-menu li, nav li").each(function ()
    {
        var depth = jQuery(this).parents('ul').length - 1;
        var indent = '';
        if (depth > 0)
        {
            indent = ' - ';
        }

        if (depth > 1)
        {
            indent = ' - - ';
        }

        if (depth > 2)
        {
            indent = ' - - -';
        }

        if (depth > 3)
        {
            indent = ' - - - -';
        }
        var el = jQuery(this).children('a');

        jQuery("<option />",
            {
                "value": el.attr("href"),
                "text": (indent + el.text())
            }).appendTo(".top-menu select, nav select");
    });

    jQuery(".top-menu select, nav select").change(function ()
    {
        window.location = jQuery(this).find("option:selected").val();
    });
}

/* bd_forms */
function bd_forms()
{
    'use strict';
    jQuery('input, textarea').placeholder();
}

/* bd_fitVids */
function bd_fitVids()
{
    'use strict';
    jQuery(".video-box, .bd-video-shortcode, .post-entry").fitVids();
}

/* bd_imgHover */
function bd_imgHover()
{
    'use strict';
    jQuery('.thumb-box img').hover( function()
        {
            jQuery(this).stop().animate(
                {
                    opacity:0.8
                },
                {
                    queue:false,
                    duration:200
                });
        },
        function()
        {
            jQuery(this).stop().animate(
                {
                    opacity:1
                },
                {
                    queue:false,
                    duration:200
                });
        }
    );
}

/* bd_goTop */
function bd_goTop()
{
    'use strict';
    jQuery(window).scroll(function()
    {
        if (jQuery(this).scrollTop() > 1)
        {
            jQuery('.gotop').css({bottom:"25px"});
        }
        else
        {
            jQuery('.gotop').css({bottom:"-100px"});
        }
    });
    jQuery('.gotop').click(function()
    {
        jQuery('html, body').animate({scrollTop: '0px'}, 800);
        return false;
    });
}

/* bd_tipsy */
function bd_tipsy()
{
    'use strict';
    jQuery('.ttip, .bd_option_item a , .titlebuilder a, .navbuilder a').tipsy({fade: true, gravity: 's'});
    jQuery('.tooldown, .tooltip-s').tipsy({fade: true, gravity: 'n'});
    jQuery('.tooltip-nw').tipsy({fade: true, gravity: 'nw'});
    jQuery('.tooltip-ne').tipsy({fade: true, gravity: 'ne'});
    jQuery('.tooltip-w').tipsy({fade: true, gravity: 'w'});
    jQuery('.tooltip-e').tipsy({fade: true, gravity: 'e'});
    jQuery('.tooltip-sw').tipsy({fade: true, gravity: 'w'});
    jQuery('.tooltip-se').tipsy({fade: true, gravity: 'e'});
}

/* initTabGroup */
function initTabGroup(content)
{
    if(typeof content == 'undefined')
    {
        content = document;
    }
    if (jQuery('.tabgroup', jQuery(content)).length)
    {
        jQuery('.tabgroup', jQuery(content)).tabs().show();
    }
}

/**
 * Rate
 */
(function(jQuery){
    function user_rating(){
        if (jQuery('.bd-user-review-rating').length)
        {
            // Get elements
            this.el = this.build_el();

            if (!this.is_rated())
            {
                // Interface fixes
                this.el.stars.top.css('background-position-y', 'top');
                this.el.stars.under.css('width', '98px');
                // Bind Events
                this.bind_events();
            }
            else
            {
                this.display_user_rating();
            }
        }
    }

    user_rating.prototype.is_rated = function ()
    {
        if (this.readCookie('bd_rating_' + bd_script.post_id) === 'rated')
        {
            return true;
        }
        else
        {
            return false;
        }
    };

    user_rating.prototype.display_user_rating = function ()
    {
        var score = this.readCookie('bd_rating_score_' + bd_script.post_id),
            position = this.readCookie('bd_rating_position_'+ bd_script.post_id);
        this.el.rating.score.html(score);
        this.el.stars.top.css('width', position + '%');
        this.el.rating.label.your_rating.show();
        this.el.rating.label.user_rating.hide();
    };

    user_rating.prototype.build_el = function ()
    {
        var el =
        {
            rating:
            {
                score:jQuery('SPAN.score', '.bd-user-review-description'),
                count:jQuery('SPAN.count', '.bd-user-review-description'),
                label:
                {
                    your_rating:jQuery('SPAN.your_rating', '.bd-user-review-description'),
                    user_rating:jQuery('SPAN.user_rating', '.bd-user-review-description')
                }
            },
            stars:
            {
                under:jQuery('.bd-criteria-star-under', '.bd-user-review-rating'),
                top:jQuery('.bd-criteria-star-top', '.bd-user-review-rating')
            }
        };

        // Plain JS style retrieval
        el.stars.old_position = parseInt(el.stars.top[0].style.width, 10);
        el.rating.old_score = el.rating.score.html();

        return el;
    };

    user_rating.prototype.bind_events = function ()
    {
        var me = this;

        // Hover effect
        me.el.stars.under.on('mouseover', function ()
        {
            // changes the sprite
            me.el.stars.top.css('background-position-y', 'bottom');

            // Changes the cursor
            jQuery(this).css('cursor', 'pointer');

            // changes the text
            me.el.rating.label.your_rating.show();
            me.el.rating.label.user_rating.hide();

        });

        me.el.stars.under.on('mouseout', function ()
        {
            // Returns the sprite
            me.el.stars.top.css('background-position-y', 'top');

            // Returns the initial position
            me.el.stars.top.css('width', me.el.stars.old_position + '%');

            // Returns the text and initial rating
            me.el.rating.label.user_rating.show();
            me.el.rating.label.your_rating.hide();
            me.el.rating.score.html(me.el.rating.old_score);

        });

        me.el.stars.under.on('mousemove', function (e)
        {
            if (!e.offsetX)
            {
                e.offsetX = e.clientX - jQuery(e.target).offset().left;
            }

            // Moves the width
            var offset = e.offsetX + 4;
            if (offset > 100)
            {
                offset = 100;
            }
            me.el.stars.top.css('width', offset + '%');

            // Update the real-time score
            var score = Math.floor(((offset / 10) * 5)) / 10;
            if (score > 5)
            {
                score = 5;
            }
            me.el.rating.score.html(score);

        });

        // Click effect
        me.el.stars.under.on('click', function (e)
        {
            if (!e.offsetX)
            {
                e.offsetX = e.clientX - jQuery(e.target).offset().left;
            }
            var count = parseInt(me.el.rating.count.html(), 10) + 1,
                score = (Math.floor(((e.offsetX + 4) / 10) * 5) / 10),
                position = e.offsetX + 4;
            if (score > 5)
            {
                score = 5;
            }

            if (position > 100)
            {
                position = 100;
            }
            // Unbind events
            me.el.stars.under.off();
            me.el.stars.under.css('cursor', 'default');

            // Stars animation
            me.el.stars.top.fadeOut(function ()
            {
                me.el.stars.top.css('background-position-y', 'top');
                me.el.stars.top.fadeIn();
            });

            // Count increment
            me.el.rating.count.html(count);

            // AJAX call to wordpress
            var req =
            {
                action:'bd_rating',
                rating_position:position,
                rating_score:score,
                post_id:bd_script.post_id
            };

            jQuery.post(bd_script.ajaxurl, req, function ()
            {
                // Save cookie
                me.createCookie('bd_rating_' + bd_script.post_id, 'rated', 900);
                me.createCookie('bd_rating_score_' + bd_script.post_id, score, 900);
                me.createCookie('bd_rating_position_' + bd_script.post_id, position, 900);
            })
        });
    };

    user_rating.prototype.createCookie = function (name, value, days)
    {
        if (days)
        {
            var date = new Date();
            date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
            var expires = "; expires=" + date.toGMTString();
        }
        else var expires = "";
        document.cookie = name + "=" + value + expires + "; path=/";
    }

    user_rating.prototype.readCookie = function (name)
    {
        var nameEQ = name + "=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++)
        {
            var c = ca[i];
            while (c.charAt(0) == ' ') c = c.substring(1, c.length);
            if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
        }
        return null;
    }

    user_rating.prototype.eraseCookie = function (name)
    {
        createCookie(name, "", -1);
    }

    jQuery(document).ready(function ()
    {
        new user_rating();
    });
})(jQuery);

// tipsy, version 1.0.0a
// (c) 2008-2010 jason frame [jason@onehackoranother.com]
// released under the MIT license
(function(a){function b(a,b){return typeof a=="function"?a.call(b):a}function c(a){while(a=a.parentNode){if(a==document)return true}return false}function d(b,c){this.$element=a(b);this.options=c;this.enabled=true;this.fixTitle()}d.prototype={show:function(){var c=this.getTitle();if(c&&this.enabled){var d=this.tip();d.find(".tipsy-inner")[this.options.html?"html":"text"](c);d[0].className="tipsy";d.remove().css({top:0,left:0,visibility:"hidden",display:"block"}).prependTo(document.body);var e=a.extend({},this.$element.offset(),{width:this.$element[0].offsetWidth,height:this.$element[0].offsetHeight});var f=d[0].offsetWidth,g=d[0].offsetHeight,h=b(this.options.gravity,this.$element[0]);var i;switch(h.charAt(0)){case"n":i={top:e.top+e.height+this.options.offset,left:e.left+e.width/2-f/2};break;case"s":i={top:e.top-g-this.options.offset,left:e.left+e.width/2-f/2};break;case"e":i={top:e.top+e.height/2-g/2,left:e.left-f-this.options.offset};break;case"w":i={top:e.top+e.height/2-g/2,left:e.left+e.width+this.options.offset};break}if(h.length==2){if(h.charAt(1)=="w"){i.left=e.left+e.width/2-15}else{i.left=e.left+e.width/2-f+15}}d.css(i).addClass("tipsy-"+h);d.find(".tipsy-arrow")[0].className="tipsy-arrow tipsy-arrow-"+h.charAt(0);if(this.options.className){d.addClass(b(this.options.className,this.$element[0]))}if(this.options.fade){d.stop().css({opacity:0,display:"block",visibility:"visible"}).animate({opacity:this.options.opacity})}else{d.css({visibility:"visible",opacity:this.options.opacity})}}},hide:function(){if(this.options.fade){this.tip().stop().fadeOut(function(){a(this).remove()})}else{this.tip().remove()}},fixTitle:function(){var a=this.$element;if(a.attr("title")||typeof a.attr("original-title")!="string"){a.attr("original-title",a.attr("title")||"").removeAttr("title")}},getTitle:function(){var a,b=this.$element,c=this.options;this.fixTitle();var a,c=this.options;if(typeof c.title=="string"){a=b.attr(c.title=="title"?"original-title":c.title)}else if(typeof c.title=="function"){a=c.title.call(b[0])}a=(""+a).replace(/(^\s*|\s*$)/,"");return a||c.fallback},tip:function(){if(!this.$tip){this.$tip=a('<div class="tipsy"></div>').html('<div class="tipsy-arrow"></div><div class="tipsy-inner"></div>');this.$tip.data("tipsy-pointee",this.$element[0])}return this.$tip},validate:function(){if(!this.$element[0].parentNode){this.hide();this.$element=null;this.options=null}},enable:function(){this.enabled=true},disable:function(){this.enabled=false},toggleEnabled:function(){this.enabled=!this.enabled}};a.fn.tipsy=function(b){function e(c){var e=a.data(c,"tipsy");if(!e){e=new d(c,a.fn.tipsy.elementOptions(c,b));a.data(c,"tipsy",e)}return e}function f(){var a=e(this);a.hoverState="in";if(b.delayIn==0){a.show()}else{a.fixTitle();setTimeout(function(){if(a.hoverState=="in")a.show()},b.delayIn)}}function g(){var a=e(this);a.hoverState="out";if(b.delayOut==0){a.hide()}else{setTimeout(function(){if(a.hoverState=="out")a.hide()},b.delayOut)}}if(b===true){return this.data("tipsy")}else if(typeof b=="string"){var c=this.data("tipsy");if(c)c[b]();return this}b=a.extend({},a.fn.tipsy.defaults,b);if(!b.live)this.each(function(){e(this)});if(b.trigger!="manual"){var h=b.live?"live":"bind",i=b.trigger=="hover"?"mouseenter":"focus",j=b.trigger=="hover"?"mouseleave":"blur";this[h](i,f)[h](j,g)}return this};a.fn.tipsy.defaults={className:null,delayIn:0,delayOut:0,fade:false,fallback:"",gravity:"n",html:false,live:false,offset:0,opacity:.8,title:"title",trigger:"hover"};a.fn.tipsy.revalidate=function(){a(".tipsy").each(function(){var b=a.data(this,"tipsy-pointee");if(!b||!c(b)){a(this).remove()}})};a.fn.tipsy.elementOptions=function(b,c){return a.metadata?a.extend({},c,a(b).metadata()):c};a.fn.tipsy.autoNS=function(){return a(this).offset().top>a(document).scrollTop()+a(window).height()/2?"s":"n"};a.fn.tipsy.autoWE=function(){return a(this).offset().left>a(document).scrollLeft()+a(window).width()/2?"e":"w"};a.fn.tipsy.autoBounds=function(b,c){return function(){var d={ns:c[0],ew:c.length>1?c[1]:false},e=a(document).scrollTop()+b,f=a(document).scrollLeft()+b,g=a(this);if(g.offset().top<e)d.ns="n";if(g.offset().left<f)d.ew="w";if(a(window).width()+a(document).scrollLeft()-g.offset().left<b)d.ew="e";if(a(window).height()+a(document).scrollTop()-g.offset().top<b)d.ns="s";return d.ns+(d.ew?d.ew:"")}}})(jQuery);

/**
 * Modern News Ticker
 * Copyright (c) CreativeTier
 * contact@CreativeTier.com
 * www.CreativeTier.com
 */
(function(e){var t={effect:"scroll",autoplay:true,feedType:"none",feedCount:5,refresh:"10:00"};var n={scroll:{scrollInterval:20,transitionTime:500},fade:{displayTime:4e3,transitionTime:300},type:{typeInterval:10,displayTime:4e3,transitionTime:300},slide:{slideDistance:100,displayTime:4e3,transitionTime:350}};var r={"rss-atom":{feedUrl:""},twitter:{twitterName:""}};var i={init:function(t){var i={};e.extend(i,{feedType:t.feedType});e.extend(i,r[i.feedType]);e.extend(i,{effect:t.effect});e.extend(i,n[i.effect]);e.extend(i,t);return this.each(function(){function w(){A();p.addClass("mt-hide");h.addClass("mt-preloader");p.children().remove();p.css("margin-left",0);d.css("opacity","1").removeClass("mt-hide");p.append(d);switch(i.feedType){case"rss-atom":e.ajax({url:"https://ajax.googleapis.com/ajax/services/feed/load?v=1.0&num="+i.feedCount+"&q="+i.feedUrl,type:"GET",dataType:"jsonp",success:function(e){var t=e.responseData.feed.entries;for(var n=0;n<t.length;n++){p.append("<li><a href='"+t[n].link+"' target='_blank'>"+t[n].title+"</a></li>")}E()}});break;case"twitter":e.ajax({url:"http://api.twitter.com/1/statuses/user_timeline.json",type:"GET",dataType:"jsonp",data:{screen_name:i.twitterName,count:i.feedCount,trim_user:true},success:function(e){for(var t=0;t<e.length;t++){p.append("<li><a href='http://twitter.com/#!/"+e[t].user.id_str+"/status/"+e[t].id_str+"' target='_blank'>"+e[t].text+"</a></li>")}E()}});break}}function E(){h.removeClass("mt-preloader");p.removeClass("mt-hide");x()}function S(){if(i.feedType=="rss-atom"||i.feedType=="twitter"){clearTimeout(f);a=false;w()}}function x(){if(i.effect!="scroll")p.children("li:not(:first)").addClass("mt-hide");if(u){u=false;if(i.autoplay){k();_()}}else O();if(i.refresh){f=setTimeout(S,T(i.refresh))}a=true}function T(e){var t;if(typeof e=="number")t=e;else{var n=e.split(":");n.reverse();t=parseFloat(n[0]);if(n[1])t+=parseFloat(n[1])*60;if(n[2])t+=parseFloat(n[2])*3600}return t*1e3}function N(e){if(a)C(e.data.type)}function C(t){if(!s){s=true;A();if(t=="prev"){switch(i.effect){case"scroll":p.css({"margin-left":-e(p.children(":last")).outerWidth()}).children(":last").prependTo(p);p.animate({"margin-left":0},i.transitionTime,function(){s=false;m.mouseleave(function(){O()})});break;case"fade":p.children(":first").animate({opacity:0},i.transitionTime,function(){e(this).addClass("mt-hide");p.children(":last").prependTo(p).removeClass("mt-hide").css({opacity:0}).animate({opacity:1},i.transitionTime,function(){O()});s=false});break;case"type":p.children(":first").animate({opacity:0},i.transitionTime,function(){e(this).addClass("mt-hide");M(p.children(":last").prependTo(p).removeClass("mt-hide").css({opacity:0}).animate({opacity:1},i.transitionTime).children("a"));s=false});break;case"slide":p.children(":first").animate({opacity:0},i.transitionTime,function(){e(this).addClass("mt-hide");p.children(":last").prependTo(p).removeClass("mt-hide").css({opacity:0,"margin-left":i.slideDistance}).animate({opacity:1,"margin-left":0},i.transitionTime,function(){O()});s=false});break}}else{switch(i.effect){case"scroll":p.animate({"margin-left":-e(p.children(":first")).outerWidth()},i.transitionTime,function(){p.css("margin-left",0).children(":first").appendTo(p);s=false;g.mouseleave(function(){O()})});break;case"fade":p.children(":first").animate({opacity:0},i.transitionTime,function(){e(this).addClass("mt-hide").appendTo(p);p.children(":first").removeClass("mt-hide").css({opacity:0}).animate({opacity:1},i.transitionTime,function(){O()});s=false});break;case"type":p.children(":first").animate({opacity:0},i.transitionTime,function(){e(this).addClass("mt-hide").appendTo(p);M(p.children(":first").removeClass("mt-hide").css({opacity:0}).animate({opacity:1},i.transitionTime).children("a"));s=false});break;case"slide":p.children(":first").animate({opacity:0},i.transitionTime,function(){e(this).addClass("mt-hide").appendTo(p);p.children(":first").removeClass("mt-hide").css({opacity:0,"margin-left":i.slideDistance}).animate({opacity:1,"margin-left":0},i.transitionTime,function(){O()});s=false});break}}}}function k(){n=true;if(i.effect=="scroll"){t=setInterval(function(){var t=parseFloat(p.css("margin-left"))-1;p.css("margin-left",t);if(Math.abs(t)>e(p.children("li")[0]).outerWidth()){p.css("margin-left",0).children(":first").appendTo(p)}},i.scrollInterval)}else{t=setInterval(function(){C("next")},i.displayTime)}}function L(){n=false;clearInterval(t)}function A(){if(n){r=true;L()}}function O(){if(r&&!o){k();r=false}}function M(e){var t=e.html().split("");var n=0;e.html("_");var r=setInterval(function(){var i=e.html().split("_")[0]+t[n++];if(n!=t.length){i+="_"}e.html(i);if(n==t.length){clearInterval(r);O()}},i.typeInterval)}function _(){y.addClass("mt-pause")}function D(){y.removeClass("mt-pause")}function P(){return false}var t;var n=false;var r=false;var s=false;var o=false;var u=true;var a=false;var f;var l=e(this);var c=l.children(".mt-label");var h=l.children(".mt-news");var p=h.children("ul");var d=p.children("li");var v=l.children(".mt-controls");var m=v.children(".mt-prev");var g=v.children(".mt-next");var y=v.children(".mt-play");if(i.effect=="scroll")l.addClass("mt-scroll");c.css("width",c.width());var b=l.width();if(c.length)b-=c.outerWidth()+parseFloat(c.css("margin-right"));if(v.length)b-=v.outerWidth()+parseFloat(v.css("margin-left"));h.css("width",b);h.hover(function(){if(a){A();o=true}},function(){if(a){o=false;O()}});m.mousedown(P).bind("click",{type:"prev"},N);g.mousedown(P).bind("click",{type:"next"},N);y.mousedown(P).click(function(){if(a){if(n){L();D()}else{k();_()}}});if(i.feedType=="rss-atom"||i.feedType=="twitter"){w()}else{x()}l.data("pause",A);l.data("resume",O);l.data("refresh",S)})},pause:function(){return this.each(function(){e(this).data("pause")()})},resume:function(){return this.each(function(){e(this).data("resume")()})},refresh:function(){return this.each(function(){e(this).data("refresh")()})}};e.fn.modernTicker=function(t){if(i[t]){return i[t].apply(this,Array.prototype.slice.call(arguments,1))}else if(typeof t==="object"||!t){return i.init.apply(this,arguments)}else{e.error("Method "+t+" does not exist on jQuery.modernTicker")}}})(jQuery)