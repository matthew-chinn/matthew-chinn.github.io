var animationDiv, height, width, numTotalPixels;
var jquery_canvas, canvas, ctx;
var picture, jquery_picture, picHeight, picWidth, picX, picY;
var imageData;

window.onload = function(){
    animationDiv = $('.animation-div');
    height = animationDiv.height();
    width = animationDiv.width();
    galaxyHeight = Math.round( height * 1 );
    galaxyWidth = Math.round( width * 1 );
    //only use pixels in this range for galaxy

    animationDiv.velocity( { backgroundColor: "#4db8ff" }, 
                           { delay: 2000, duration: 3000} );

    jquery_canvas = $('#myCanvas');
    jquery_canvas.attr( { width: width, height: height } );

    canvas = jquery_canvas[0];
    ctx = canvas.getContext("2d");

    picture = new Image();
    picture.src= "/images/aboutpic.png";

    picWidth = 160;
    picHeight = picWidth * 1.5; 
    picX = width / 2 - picWidth / 2, picY = height - picHeight - 20;

    picture.onload = function() {

        ctx.drawImage( picture, picX, picY, picWidth, picHeight );

        //get pixels
        imageData = ctx.getImageData( picX, picY, picWidth, picHeight );
        ctx.clearRect(0,0,width,height);
        var pixels = imageData.data;

        //for starry particles
        numTotalPixels = galaxyWidth * galaxyHeight;
       
        //num of pixels in original image
        var numPixels = pixels.length / 4;

        var spaceBetweenPixels = Math.round( numTotalPixels / numPixels );

        var originalRepeatNum = 20;
        var repeatNum = originalRepeatNum;
        var lightenFactor = 300; //make pixels brighter
        var factorDecrease = lightenFactor / repeatNum; //change^factor
        var speed = 400;
        var easingFactor = .8;

        var gHeightChange = (galaxyHeight - picHeight) / repeatNum,
            gWidthChange = (galaxyWidth - picWidth) / repeatNum;
        var finalHeightOffset = 6;

        var galaxyX = 0, galaxyY = 0; //where the galaxy pic should be placed
        var gXChange = gWidthChange / 2,
            gYChange = (gHeightChange + finalHeightOffset) / 2;

        var galaxyAnimation = function(){
            ctx.clearRect(0,0,width,height);
            numTotalPixels = galaxyHeight * galaxyWidth;

            var galaxyImageData = ctx.createImageData( galaxyWidth, 
                                                       galaxyHeight );
            var galaxyPixels = galaxyImageData.data;

            var index;
            for ( var i = 0; i < pixels.length; i += 4 ){
                index = Math.round( Math.random() * galaxyPixels.length );
                galaxyPixels[index] = pixels[i] + lightenFactor;
                galaxyPixels[index+1] = pixels[i+1] + lightenFactor;
                galaxyPixels[index+2] = pixels[i+2] + lightenFactor;
                galaxyPixels[index+3] = 255;
            }

            lightenFactor = Math.max( lightenFactor - factorDecrease, 0 );
            ctx.putImageData(galaxyImageData, galaxyX, galaxyY);
            galaxyHeight -= gHeightChange;
            galaxyWidth -= gWidthChange;
            galaxyX += gXChange;
            galaxyY += gYChange;
            repeatNum--;
            speed *= easingFactor;

            if( repeatNum === 0 ) {
                ctx.clearRect(0,0,width,height);
                ctx.drawImage( picture, picX, picY, picWidth, picHeight );
            }

            //repeat with new speed
            if ( repeatNum !== 0 )
                if ( repeatNum === originalRepeatNum - 1 ){
                    window.setTimeout( galaxyAnimation, 3000 );
                }
                else{
                    window.setTimeout( galaxyAnimation, speed );
                }
        }

        galaxyAnimation();
        
    }
};

