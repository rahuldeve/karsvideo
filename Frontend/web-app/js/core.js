

$( function() {

    $('.seg').click(
        function(event,pointer) {

            console.log('asdasdasd');
            var target = $(event.target);
            var item = target.attr('id');
            console.log(item+'-text');

            $('.seg-details').removeClass('visible');

            $('#' + item+'-details').addClass('visible');
        });







    function onSegClick(event,pointer){
        var target = $(event.target);
        var item = target.attr('id');

        $(item+'-text').toggleClass('visible');

    }

    function onClick( event,pointer ) {

        var target = event.target;
        var $target = $( target );


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








        if ( isExpanded ) {
            // if shrinking, just layout
            $container.packery();
        } else {
            // if expanding, fit it
            $container.packery( 'fit', $itemElem[0] );
        }

    }






    function onhover( event,pointer ) {

        var target = event.target;
        var $target = $( target );


        var $itemElem = $target.parent();
        var isExpanded = $itemElem.hasClass('on-hover');
        if(isExpanded){
            container_first_row.children().removeClass('expand');
            segbar.removeClass('expand');
            //container_third_row.removeClass('expand');
        }

        else{
            container_first_row.children().addClass('expand');
            segbar.addClass('expand');
            //container_third_row.addClass('expand');
        }


        if ( isExpanded ) {
            // if shrinking, just layout
            $container.packery();
        } else {
            // if expanding, fit it
            $container.packery( 'fit', $itemElem[0] );
        }

    }













    var $container = $('.packery').packery({
        gutter:10
    });

    $container.find('.item').each( function( i, itemElem ) {

        // make element draggable with Draggabilly
        var draggie = new Draggabilly( itemElem );

        draggie.on('staticClick',onClick);

        //bind Draggabilly events to Packery
        $container.packery( 'bindDraggabillyEvents', draggie );



    });

});
