var FORWARD=!0,BACKWARD=!1,RIGHT=!0,LEFT=!1,cookieX,cookieY,runnerX,runnerY,cookie,runnerSpeed,repeat,prevFace,runnerbutton,runnerBody,runnerFatBody,runnerRLeg,runnerLLeg,runnerRArm,runnerLArm,runnerREye,runnerMouth,animationDiv,height,width;
$(document).ready(function(){runner=$("#runner");runnerbutton=$("#runnerbutton");cookie=$("#cookie");runnerX=runner.css("left");runnerY=runner.css("top");runnerSpeed=5;animationDiv=$(".animation-div");height=animationDiv.height();width=animationDiv.width();runnerbutton.css({margin:"0 auto",position:"absolute",bottom:10,right:10});runnerbutton.click(function(a){runner.removeClass("hide");runnerbutton.addClass("hide");runnerBody=$("#runner svg #body");runnerFatBody=$("#runner svg #fatbody");runnerRLeg=
$("#runner svg #rightleg");runnerLLeg=$("#runner svg #leftleg");runnerRArm=$("#runner svg #rightarm");runnerLArm=$("#runner svg #leftarm");runnerREye=$("#runner svg #righteye");runnerMouth=$("#runner svg #mouth");"hidden"===runnerBody.css("visibility")&&(runnerBody.css("visibility","visible"),runnerFatBody.css("visibility","hidden"));prevFace=RIGHT;cookie.removeClass("hide");layTreat(a);$(animationDiv).click(function(a){layTreat(a)});chaseTheCookie()})});
function chaseTheCookie(){var a=RIGHT;repeat=!0;var b=0,c=0;runnerX=parseInt(runner.css("left"));runnerY=parseInt(runner.css("top"));runnerX+20-cookieX>runnerSpeed?(b=-1*runnerSpeed,a=LEFT):runnerX+20-cookieX<-1*runnerSpeed&&(b=runnerSpeed,a=RIGHT);runnerY+30-cookieY>runnerSpeed?c=-1*runnerSpeed:runnerY+30-cookieY<-1*runnerSpeed&&(c=runnerSpeed);0===b&&0===c&&(repeat=!1,cookie.addClass("hide"),runnerBody.css("visibility","hidden"),runnerFatBody.css("visibility","visible"),runnerbutton.removeClass("hide"));
prevFace!=a&&(switchDirection(),prevFace=a);runAnimation(a,200);runner.velocity({left:""+(runnerX+b)+"px",translateZ:0},{duration:200,queue:!1}).velocity({top:""+(runnerY+c)+"px"},{duration:200,queue:!1});repeat&&setTimeout(function(){chaseTheCookie()},400)}
var legAngle=3,rightLegNormal="M23,40 l0,10",rightLegRunRight1="M23,40 l"+legAngle+",5 l-5,5",rightLegRunRight2="M23,40 l"+legAngle+",10",rightLegRunLeft1="M23,40 l"+-1*legAngle+",5 l5,5",rightLegRunLeft2="M23,40 l"+legAngle+",10",leftLegNormal="M17,40 l0,10",leftLegRunRight1="M17,40 l"+legAngle+",5 l-5,5",leftLegRunRight2="M17,40 l"+-1*legAngle+",10",leftLegRunLeft1="M17,40 l"+-1*legAngle+",5 l5,5",leftLegRunLeft2="M17,40 l"+-1*legAngle+",10",step=!0;
function runAnimation(a,b){runnerRLeg.attr("d",rightLegNormal);runnerLLeg.attr("d",leftLegNormal);setTimeout(function(){a===RIGHT?step?(runnerRLeg.attr("d",rightLegRunRight1),runnerLLeg.attr("d",leftLegRunRight2)):(runnerRLeg.attr("d",rightLegRunRight2),runnerLLeg.attr("d",leftLegRunRight1)):a===LEFT&&(step?(runnerLLeg.attr("d",leftLegRunLeft1),runnerRLeg.attr("d",rightLegRunLeft2)):(runnerLLeg.attr("d",leftLegRunLeft2),runnerRLeg.attr("d",rightLegRunLeft1)));step=!step},b/2)}
var leftArmDataOne="M20,30 l1,5 l5,0",leftArmDataTwo="M20,30 l-1,5 l-5,0",rightArmDataOne="M27,33 l2,0",rightArmDataTwo="M13,33 l-2,0";function switchDirection(){var a=parseInt(runnerREye.attr("cx"))-20;runnerREye.attr("cx",""+(20+-1*a));a=parseInt(runnerMouth.attr("cx"))-20;runnerMouth.attr("cx",""+(20+-1*a));runnerLArm.attr("d")===leftArmDataOne?(runnerLArm.attr("d",leftArmDataTwo),runnerRArm.attr("d",rightArmDataTwo)):(runnerLArm.attr("d",leftArmDataOne),runnerRArm.attr("d",rightArmDataOne))}
function layTreat(a){cookieCompensate=parseInt(cookie.css("width"))/2;cookieX=a.pageX-cookieCompensate;cookieY=a.pageY-cookieCompensate;console.log(cookieY);cookie.css("top",cookieY).css("left",cookieX)};var fullNameWidth,fullName,centerNameFactor,nameHeight,divHeight,halfHeight,divWidth,rChange,gChange,bChange,red=0,green=102,blue=255,headerStrip,headerFiller,topButton,pic,picDiv,picTop,isMobile=!1,isSmall=!1;
$(document).ready(function(){/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)&&(isMobile=!0);fullName=$("#fullname");animationDiv=$(".animation-div");animationDiv.css("background-color",rgb(red,green,blue));headerStrip=$(".header-strip");headerFiller=$(".header-filler");topButton=$(".top-button");rChange=(121-red)/halfHeight;gChange=(198-green)/halfHeight;bChange=(236-blue)/halfHeight;calibrate();centerName();pic=$("#me-picture");picDiv=$(".my-picture-div");
picTop=parseInt(picDiv.css("top"));$(function(){$('a[href*="#"]:not([href="#"])').click(function(){if("#carousel-container"!=$(this).attr("href")&&location.pathname.replace(/^\//,"")==this.pathname.replace(/^\//,"")&&location.hostname==this.hostname){var a=$(this.hash),a=a.length?a:$("[name="+this.hash.slice(1)+"]");if(a.length)return $("html, body").animate({scrollTop:a.offset().top},1E3),!1}})})});$(window).on("resize",function(){centerName();calibrate()});$(window).scroll(function(){scrollTransition(this)});
function scrollTransition(a){a=$(a).scrollTop();if(a>halfHeight&&a<divHeight){var b=Math.round(red+rChange*(a-halfHeight)),c=Math.round(green+gChange*(a-halfHeight)),d=Math.round(blue+bChange*(a-halfHeight));animationDiv.css("backgroundColor",rgb(b,c,d));b=Math.round((divHeight-a)/halfHeight*100)/100;pic.css("opacity",b)}else a<halfHeight&&(animationDiv.css("backgroundColor",rgb(red,green,blue)),pic.css("opacity",1));picDiv.css("top",picTop+(divHeight-picTop)/divHeight*a);a>=divHeight?(headerStrip.css({position:"fixed",
top:"0px"}),headerFiller.css({display:"block"}),topButton.css("display","inline-block")):(headerStrip.css({position:"static",top:"auto"}),headerFiller.css({display:"none"}),topButton.css("display","none"))}function rgb(a,b,c){return"rgb("+a+","+b+","+c+")"}function centerName(){calibrate();fullName.css({top:nameHeight,left:centerNameFactor,position:"absolute"})}
function calibrate(){fullNameWidth=parseInt($("#fullname .animation-name").css("width"));nameHeight=parseInt($("#fullname .animation-name").css("height"));nameHeight/=2;divHeight=animationDiv.height();divWidth=animationDiv.width();halfHeight=divHeight/2;centerNameFactor=divWidth/2-fullNameWidth/2};
