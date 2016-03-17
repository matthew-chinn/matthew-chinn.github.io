var firstNameWidth, lastNameWidth, animationDiv;
var firstName, lastName;
var nameHeight, headerHeight, divHeight, halfHeight;
var centerNameFactor; //left and right distance for full name
var divWidth;
var rChange, gChange, bChange;
var red = 0, green = 102, blue = 255; //"#4db8ff" beginning background color

var pic;
var picDiv, picTop;
var isMobile = false, isSmall = false;

$(document).ready(function(){
    if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
        isMobile = true;
    }

    firstNameWidth = parseInt( $('#firstname .animation-name').attr( "width" ));
    lastNameWidth = parseInt( $('#lastname .animation-name').attr( "width" ) );
    nameHeight = parseInt( $('#firstname .animation-name').attr( "height" ) );
    nameHeight /= 2;

    firstName = $('#firstname');
    lastName = $('#lastname');

    animationDiv = $('.animation-div'); //bounding div
    animationDiv.css( "background-color", rgb(red,green,blue) );

    divHeight = animationDiv.height();
    divWidth = animationDiv.width();
    headerHeight = $('header').height();
    halfHeight = divHeight / 2;

    //first number is the rgb for the ending color
    rChange = (121- red) / halfHeight;
    gChange = (198- green) / halfHeight;
    bChange = (236 - blue) / halfHeight;
    
    centerName();

    pic = $('#me-picture');
    picDiv = $('.my-picture-div');
    picTop = parseInt(picDiv.css( "top" ));
});

//keep name centered if animation not moving
$(window).on('resize', function(){
    if( $(this).scrollTop() === 0 ){
        centerName();
        calibrate();
    }
});

$(window).scroll(function(){
    if ( !isMobile && !isSmall ){
        scrollTransition(this)
    }
});

//on scroll, make names circle down, color turn to white
function scrollTransition(w){
    var scrolledAmt = $(w).scrollTop();
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

    //name animations
    var nameHMoveAmt = centerNameFactor / divHeight;
    var nameVMove = divHeight - nameHeight; //amount names have to move down
    var nameVMoveAmt = nameVMove / divHeight;
    var finalAngle = 60; //final angle for names
    var rotate = finalAngle / divHeight * scrolledAmt;
    /*
    firstName.css({ "left": centerNameFactor - nameHMoveAmt * scrolledAmt,
                    "top": nameHeight + nameVMoveAmt * scrolledAmt });
                  //  "transform": "rotateZ(-" + rotate + "deg)" });
    lastName.css({ "right": centerNameFactor - nameHMoveAmt * scrolledAmt,
                    "top": nameHeight + nameVMoveAmt * scrolledAmt });
                  //  "transform": "rotateZ(" + rotate + "deg)" });
                    */

    var picVMoveAmt = (divHeight - picTop) / divHeight; 
    //amount for pic to Move down
    picDiv.css( "top", picTop + picVMoveAmt * scrolledAmt );

}

//return string version of rgb 
function rgb(r,g,b) {
    return "rgb(" + r + "," + g + "," + b + ")";
}

function centerName() {
    calibrate();
    firstName.css( { "top": nameHeight,
                     "left": centerNameFactor,
                     "position": "absolute" } );
    lastName.css( { "top": nameHeight,
                    "right": centerNameFactor,
                    "position": "absolute" } );
}

//calibrate width and height
function calibrate() {
    var overlap = 35;
    var fullNameWidth = firstNameWidth + lastNameWidth - overlap;
    height = animationDiv.height();
    width = animationDiv.width();
    centerNameFactor = width / 2 - fullNameWidth / 2;

    if ( centerNameFactor < 50 ) { isSmall = true; } //set true if small window
    else { isSmall = false; }

    if ( isMobile || isSmall ){
        var scale = width / fullNameWidth;
        var overlap = overlap * (scale+1);
        fullNameWidth = firstNameWidth + lastNameWidth - overlap;
        centerNameFactor = width / 2 - fullNameWidth / 2;
        if ( scale < 1 ){
            $('.name-div').css( "transform", "scale(" + scale + "," + scale + ")");
            firstName.css( "left", centerNameFactor );
            lastName.css( "right", centerNameFactor );
        }
    }
    else{
        $('.name-div').css( "transform", "scale(1,1)");
    }

}
