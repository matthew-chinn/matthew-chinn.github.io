var FORWARD = true, BACKWARD = false, RIGHT = true, LEFT = false;

var cookieX, cookieY, runnerX, runnerY, cookie, runnerSpeed, repeat, prevFace;
var runnerbutton, runnerBody, runnerFatBody, runnerRLeg, runnerLLeg, 
    runnerRArm, runnerLArm, runnerREye, runnerMouth;
var animationDiv, height, width;
var navbar;

$(document).ready(function(){
    runner = $('#runner');
    runnerbutton = $('#runnerbutton');
    cookie = $('#cookie');
    runnerX = runner.css("left");
    runnerY = runner.css("top");
    runnerSpeed = 5;

    animationDiv = $('.animation-div');
    height = animationDiv.height();
    width = animationDiv.width();
    navbar = $('.header-strip');

    runnerbutton.css( { margin: "0 auto", position: "absolute", bottom: 10,
                        right: 10 } );

    runnerbutton.click(function(e){
        runner.removeClass("hide");
        runnerbutton.addClass("hide");

        //initialize selector variables
        runnerBody = $('#runner svg #body');
        runnerFatBody = $('#runner svg #fatbody');
        runnerRLeg = $('#runner svg #rightleg');
        runnerLLeg = $('#runner svg #leftleg');
        runnerRArm = $('#runner svg #rightarm');
        runnerLArm = $('#runner svg #leftarm');
        runnerREye = $('#runner svg #righteye');
        runnerMouth = $('#runner svg #mouth');

        //reset body
        if(runnerBody.css("visibility") === "hidden")
        {
            runnerBody.css("visibility","visible");
            runnerFatBody.css("visibility", "hidden");
        }

        prevFace = RIGHT;
        cookie.removeClass("hide");
        layTreat(e);
        $(animationDiv).click(function(e){
            layTreat(e);
        });
        chaseTheCookie();
    });
});

//runner chase the cookie
function chaseTheCookie()
{
    var faceNow = RIGHT; //direction facing now
    repeat = true; //if runner should keep moving
    var right = 0; //where to move
    var down = 0; //where to move
    var time = 200;
    runnerX = parseInt(runner.css("left"));
    runnerY = parseInt(runner.css("top"));
    var xCompensate = 20; //center the runner
    var yCompensate = 30;

    if(runnerX + xCompensate - cookieX > runnerSpeed)
    {
        right = -1 *runnerSpeed;
        faceNow = LEFT;
    }
    else if(runnerX + xCompensate - cookieX < (-1 * runnerSpeed))
    {
        right = runnerSpeed;
        faceNow = RIGHT;
    }
    if(runnerY + yCompensate - cookieY > runnerSpeed)
        down = -1 * runnerSpeed;
    else if(runnerY+ yCompensate - cookieY < (-1 * runnerSpeed))
        down = runnerSpeed;

    if(right === 0 && down === 0)
    {
        repeat = false;
        cookie.addClass("hide");
        runnerBody.css("visibility","hidden");
        runnerFatBody.css("visibility", "visible");
        runnerbutton.removeClass("hide");
    }

    if(prevFace != faceNow) //face should swap
    {
        switchDirection();
        prevFace = faceNow;
    }

    runAnimation(faceNow, time);

    //translateZ for hardware acceleration
    runner.velocity({left:""+(runnerX+right)+"px", translateZ:0},
                    {duration:time, queue:false})
          .velocity({top:""+(runnerY+down)+"px"},
                    {duration:time, queue:false});
    if(repeat)
        setTimeout(function(){chaseTheCookie();}, time*2);
}
        
var legAngle = 3;
var rightLegNormal = "M23,40 l0,10";
var rightLegRunRight1 = "M23,40 l"+legAngle+",5 l-5,5";
var rightLegRunRight2 = "M23,40 l"+legAngle+",10";
var rightLegRunLeft1 = "M23,40 l"+(-1*legAngle)+",5 l5,5";
var rightLegRunLeft2 = "M23,40 l"+legAngle+",10";

var leftLegNormal = "M17,40 l0,10";
var leftLegRunRight1 = "M17,40 l"+legAngle+",5 l-5,5";
var leftLegRunRight2 = "M17,40 l"+(-1*legAngle)+",10";
var leftLegRunLeft1 = "M17,40 l"+(-1*legAngle)+",5 l5,5";
var leftLegRunLeft2 = "M17,40 l"+(-1*legAngle)+",10";
var step = true;

function runAnimation(facing, time)
{
    runnerRLeg.attr("d",rightLegNormal);
    runnerLLeg.attr("d", leftLegNormal);

    setTimeout(function(){
        if(facing === RIGHT)
        {
            if(step)
            {
                runnerRLeg.attr("d", rightLegRunRight1);
                runnerLLeg.attr("d", leftLegRunRight2);
            }
            else
            {
                runnerRLeg.attr("d", rightLegRunRight2);
                runnerLLeg.attr("d", leftLegRunRight1);
            }
        }
        else if(facing === LEFT)
        {
            if(step)
            {
                runnerLLeg.attr("d", leftLegRunLeft1);
                runnerRLeg.attr("d", rightLegRunLeft2);
            }
            else
            {
                runnerLLeg.attr("d", leftLegRunLeft2);
                runnerRLeg.attr("d", rightLegRunLeft1);
            }
        }
        step = !step;
    }, time/2);
}
        
var leftArmDataOne = "M20,30 l1,5 l5,0";
var leftArmDataTwo = "M20,30 l-1,5 l-5,0";
var rightArmDataOne = "M27,33 l2,0";
var rightArmDataTwo = "M13,33 l-2,0";

function switchDirection()
{
    var center = 20; //center of the face, where left eye is
    var eyeOffset = parseInt(runnerREye.attr("cx")) - center;
    eyeOffset *= -1; //flip
    runnerREye.attr("cx", ""+(center + eyeOffset));
    var mouthOffset = parseInt(runnerMouth.attr("cx")) - center;
    mouthOffset *= -1;
    runnerMouth.attr("cx",""+(center + mouthOffset));
    if(runnerLArm.attr("d") === leftArmDataOne) //facing right
    {
        runnerLArm.attr("d", leftArmDataTwo);
        runnerRArm.attr("d", rightArmDataTwo);
    }
    else
    {
        runnerLArm.attr("d", leftArmDataOne);
        runnerRArm.attr("d", rightArmDataOne);
    }
}
    
//update mouseX and Y
function layTreat(e)
{
    cookieCompensate = parseInt(cookie.css("width")) / 2;
    cookieX = e.pageX - cookieCompensate;
    cookieY = e.pageY - navbar.height(); 
    console.log(cookieY);

    cookie.css("top",cookieY)
          .css("left", cookieX);
}
