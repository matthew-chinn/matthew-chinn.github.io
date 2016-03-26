var fullNameWidth, animationDiv;
var fullName;
var centerNameFactor;
var nameHeight, divHeight, halfHeight;
var divWidth;
var rChange, gChange, bChange;
var red = 0, green = 102, blue = 255; //"#4db8ff" beginning background color

var headerStrip, headerFiller, topButton;
var headerIcons, iconOriginalBottom, iconOriginalFinal;

var pic;
var picDiv, picTop;
var isMobile = false, isSmall = false;

$(document).ready(function(){
    if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
        isMobile = true;
    }

    fullName = $('#fullname');

    animationDiv = $('.animation-div'); //bounding div
    animationDiv.css( "background-color", rgb(red,green,blue) );

    headerStrip = $('.header-strip');
    headerFiller = $('.header-filler');
    topButton = $('.top-button');
    headerIcons = $('.nav-icon');
    iconOriginalBottom = parseInt( headerIcons.css("bottom") ); //original value
    iconOriginalFinal = 10;

    calibrate();
    centerName();

    //first number is the rgb for the ending color
    rChange = (121- red) / halfHeight;
    gChange = (198- green) / halfHeight;
    bChange = (236 - blue) / halfHeight;
    
    pic = $('#me-picture');
    picDiv = $('.my-picture-div');
    picTop = parseInt(picDiv.css( "top" ));

    //scroll to button when clicked
    $(function() {
      $('a[href*="#"]:not([href="#"])').click(function() {
        if( $(this).attr("href")=="#carousel-container") return; //dont include arrows

        if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'') && location.hostname == this.hostname) {
          var target = $(this.hash);
          target = target.length ? target : $('[name=' + this.hash.slice(1) +']');
          if (target.length) {
            $('html, body').animate({
              scrollTop: target.offset().top
            }, 1000);
            return false;
          }
        }
      });
    });
});

//keep name centered if animation not moving
$(window).on('resize', function(){
    centerName();
    calibrate();
});

$(window).scroll(function(){
    //if ( !isMobile && !isSmall ){
        scrollTransition(this)
    //}
});

//on scroll, color turn to white
function scrollTransition(w){
    var scrolledAmt = $(w).scrollTop();

    if( !isMobile && !isSmall ){
        //background color and picture
        if( scrolledAmt > halfHeight && scrolledAmt < divHeight ){
            var r = Math.round(red + rChange * (scrolledAmt-halfHeight)),
                g = Math.round(green + gChange * (scrolledAmt-halfHeight)),
                b = Math.round(blue + bChange * (scrolledAmt-halfHeight));
            animationDiv.css( "backgroundColor", rgb(r,g,b) );
            var opacity = Math.round((divHeight-scrolledAmt) / halfHeight *100)/100;
            pic.css("opacity", opacity);
        }
        else if( scrolledAmt < halfHeight ){
            animationDiv.css( "backgroundColor", rgb(red,green,blue));
            pic.css("opacity", 1);
        }

        var picVMoveAmt = (divHeight - picTop) / divHeight; 
        //amount for pic to Move down
        picDiv.css( "top", picTop + picVMoveAmt * scrolledAmt );
    }



    //header strip stick to top
    if( scrolledAmt >= divHeight ){
        headerStrip.css({ position: "fixed", top: "0px" });
        headerFiller.css({ display: "block" });
    }
    else{
        headerStrip.css({ position: "static", top: "auto" });
        headerFiller.css({ display: "none" });
        headerIcons.css({ bottom: iconOriginalBottom }); //whatever original value was
    }
    
    //for the buttons to scroll down appropriately
    var change = iconOriginalBottom - iconOriginalFinal;
    var diff = scrolledAmt - ( divHeight - change );
    if ( diff > 0 && diff <= iconOriginalFinal ){
        headerIcons.css({ bottom: iconOriginalBottom - diff + "px" });
    }
    else if( diff > iconOriginalFinal ){
        headerIcons.css({ bottom: iconOriginalFinal + "px" });
    }
    
}

//return string version of rgb 
function rgb(r,g,b) {
    return "rgb(" + r + "," + g + "," + b + ")";
}

function centerName() {
    calibrate();
    if( isMobile ){
        fullName.css( {"top":"auto",
                       "bottom": "10px",
                       "left": centerNameFactor,
                       "position": "absolute" } );
    }else{
        fullName.css( { "top": nameHeight,
                         "left": centerNameFactor,
                         "position": "absolute" } );
    }
}

//calibrate width and height
function calibrate() {
    fullNameWidth = parseInt( $('#fullname .animation-name').css( "width" ));
    nameHeight = parseInt( $('#fullname .animation-name').css( "height" ) );
    nameHeight /= 2;

    divHeight = animationDiv.height();
    divWidth = animationDiv.width();
    halfHeight = divHeight / 2;

    centerNameFactor = divWidth / 2 - fullNameWidth / 2;


    /*
    if ( condition ) { isSmall = true; } //set true if small window
    else { isSmall = false; }

    if ( isMobile || isSmall ){
        //just in case
    }
    */

}
