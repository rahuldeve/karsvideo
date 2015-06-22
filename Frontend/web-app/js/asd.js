/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


// http://packery.metafizzy.co/packery.pkgd.js added as external resource

$( function() {
    var $container = $('.packery').packery({
        gutter:10
    });

    $container.find('.item').each( function( i, itemElem ) {
        // make element draggable with Draggabilly
        var draggie = new Draggabilly( itemElem );
        // bind Draggabilly events to Packery
        $container.packery( 'bindDraggabillyEvents', draggie );
    });

    $container.on( 'click', '.item-content', function( event ) {
        var target = event.target;
        var $target = $( target );




        // disable transition
        //$target.css( transitionProp, 'none' );
        // set current size
        //$target.css({
        //  width: $target.width(),
        //  height: $target.height()
        // });

        var $itemElem = $target.parent();
        var isExpanded = $itemElem.hasClass('is-expanded');
        $itemElem.toggleClass('is-expanded');

        //var container_first_row = $('.container-first-row');
        var container_first_row = $itemElem.find('.container-first-row');
        var segbar=$itemElem.find('.segbar');
        var container_third_row = $itemElem.find('.container-third-row');

        if(isExpanded){
            container_first_row.children().removeClass('expand');
            segbar.removeClass('expand');
            container_third_row.removeClass('expand');
        }

        else{
            container_first_row.children().addClass('expand');
            segbar.addClass('expand');
            container_third_row.addClass('expand');
        }


        //console.log(content.children.toggleClass('is_expanded'));
        // force redraw
        //var redraw = target.offsetWidth;
        // renable default transition
        // target.style[ transitionProp ] = '';

        // reset 100%/100% sizing after transition end
        /* if ( transitionProp ) {
         $target.one( transitionEndEvent, function() {
         target.style.width = '';
         target.style.height = '';
         });
         }
         */
        // set new size
        /*$target.css({
         width: $itemElem.width(),
         height: $itemElem.height()
         });*/

        if ( isExpanded ) {
            // if shrinking, just layout
            $container.packery();
        } else {
            // if expanding, fit it
            $container.packery( 'fit', $itemElem[0] );
        }

    });
});