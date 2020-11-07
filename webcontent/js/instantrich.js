//Common

function trHighlight(tr ){
	$("#" + tr ).highlightFade({color:'yellow',speed:500,iterator:'exponential'});
}

function trHighlightItem(tr){
	$("#" + tr ).highlightFade({color:'black',speed:500,iterator:'exponential', attr:'color'});
}

		
function hideAllBubblePopupsDelayed(){
	setTimeout("hideAllBubblePopups()", 2000);
}

function hideAllBubblePopups(){
	$(".bb").HideAllBubblePopups();
}


//Index
function showDetailOn(elm){
	elm.style.opacity = 0.25;
   	elm.style.filter  = "alpha(opacity=25)";
   	elm.style.MozOpacity=0.25;
}
function showDetailOff(elm){
	elm.style.opacity = 1;
   	elm.style.filter  = "alpha(opacity=100)";
   	elm.style.MozOpacity=1;   
}

function setBuyingToFalse(){
	opening = false;
}

var opening = false;

function boxBuy(g,p,b){
	if( opening == false){
		opening = true;
		hideAllBubblePopups();
		
		var button=document.getElementById('buyButton'+g);
		if( button != null){
			if( button.src.indexOf('btn_destapar_disabled.png') != -1){
				opening = false;
				return;
			}
		}
		
		disableButton(g);
	
		$.post("https://www.instantri.ch/do/boxBuy", { g:g, p:p, b:b}, function(data){
			var tokens = data.split("#");
			if( tokens[0] == '1' || tokens[0] == '2' || tokens[0] == '3' || tokens[0] == '4' || tokens[0] == '5' || tokens[0] == '6' || tokens[0] == '7' || tokens[0] == '8' || tokens[0] == '9' || tokens[0] == 'a'){
				if( tokens[0] == '1'){
					var text = "The new price is " + tokens[1] + "credits.<br/> Do you still want to open the box with this price?<br/><br/><input type='button' value='Yes' onclick='javascript:boxBuy("+g+","+tokens[1]+",-1,document.getElementById(\"buyButton"+g+"\"));'/>&nbsp;<input type='button' value='No'  onclick='javascript:hideAllBubblePopups();'/>"
					enableButton(g);
				}
				if( tokens[0] == '2'){
					var text = "You don't have enough credits to open this box.<br/>Do you want to buy more credits?<br/><br/><input type='button' value='Yes' onclick=\"document.location.href='/do/credits/buy'\"/>&nbsp;<input type='button' value='No'  onclick='javascript:hideAllBubblePopups();'/>";
					enableButton(g);
				}
				if( tokens[0] == '3'){
					var text = "The game is over.";
					disableButton(g);
				}
				if( tokens[0] == '4'){
					var text = "If you want to open boxes you neeed to be registered.<br/>Do you want to register in Instantri.ch now?<br/><br/><input type='button' value='Yes' style='width:40px;' onclick='document.location.href=\"/register\"'/>&nbsp;<input type='button' value='No' style='width:40px;' onclick='javascript:hideAllBubblePopups();'/>";
					enableButton(g);
				}
				if( tokens[0] == '5'){
					var text = "You cannot open a box when<br/>you found the prize position.";
					disableButton(g);
				}
				if( tokens[0] == '6'){
					var text = "This game is only for beginners.";
					enableButton(g);
				}
				if( tokens[0] == '7'){
					var text = "This box has been already opened.";
					enableButton(g);
				}
				if( tokens[0] == '8'){
					var text = "In free games you can<br/>only open one box per game.";
					enableButton(g);
				}
				if( tokens[0] == '9'){
					var text = "You cannot participate in free games<br/>if your email address is not validated.";
					enableButton(g);
				}
			
				if( tokens[0] == 'a'){
					var text = "Your user is marked as fraudulent.<br/>You cannot open boxes in any game.";
					enableButton(g);
				}
				
				var options =	{ 
					themePath : '/jquerybubblepopup-theme/',
					mouseOver: 'hide',
					innerHtml:  text
				};
				
				$('#buyButton'+g).CreateBubblePopup(options);
				$('#buyButton'+g).ShowBubblePopup();
				$('#buyButton'+g).FreezeBubblePopup();
				if( tokens[0] == '3' || tokens[0] == '5' || tokens[0] == '6' || tokens[0] == '7' || tokens[0] == '8' || tokens[0] == '9' || tokens[0] == 'a'){
					setTimeout("$('#buyButton"+g+"').HideBubblePopup()", 1800);
				}
				
				opening = false;
			} else{
				sessionTimeOutMinutes = 59;
			}
	 	});
	 }
}


var segundos = new Array(8) 
var minutos = new Array(8);
var cronoId = new Array(8);

function initCrono(id, s) {
	segundos[id] = s % 60;
	minutos[id] = Math.floor(s /60);
	var valorCrono = "";
	valorCrono = (minutos[id] < 10) ? "0" + minutos[id] : minutos[id];
	valorCrono += (segundos[id] < 10) ? ":0" + segundos[id] : ":" + segundos[id];
	var crono = document.getElementById("crono" + id);
	if( crono != null){
		crono.innerHTML = valorCrono;
	}
}

function stopCrono(id){
  clearTimeout(cronoId[id]);
}

function showCrono(id) {
	segundos[id]--;
	if ( segundos[id] < 0 ) {
		segundos[id] = 59;
		minutos[id]--;
		if ( minutos[id] < 0 ) {
			stopCrono[id];
			return true;
		}
	}

	var valorCrono = "";
	valorCrono = (minutos[id] < 10) ? "0" + minutos[id] : minutos[id];
	valorCrono += (segundos[id] < 10) ? ":0" + segundos[id] : ":" + segundos[id];
	var crono = document.getElementById("crono" + id);
	if( crono != null){
  		crono.innerHTML = valorCrono;
  		cronoId[id] =setTimeout("showCrono("+id+")", 1000);
		return true;
	}
}

function disableButton(id){
	var elm=document.getElementById('buyButton'+id);
	if( elm != null){
		elm.src="/img/btn_destapar_disabled.png";
	}
	
}

function enableButton(id){
	var elm=document.getElementById('buyButton'+id);
	if( elm != null){
		elm.src="/img/btn_destapar.png";
	}
}


//Item

function boxBuyItem(g,b){

	if( opening == false){
		opening = true;
		hideAllBubblePopups();
		var button=document.getElementById('buyButton');
		if( button != null){
			if( button.src.indexOf('btn_destapar_disabled.png') != -1){
				opening = false;
				return;
			}
		}
		
		disableButtonItem();
		
		var p = document.getElementById("boxPrice").innerHTML;
	
		$.post("https://www.instantri.ch/do/boxBuy", { g:g, p:p, b:b}, function(data){
			var tokens = data.split("#");
			if( tokens[0] == '1' || tokens[0] == '2' || tokens[0] == '3' || tokens[0] == '4' || tokens[0] == '5' || tokens[0] == '6' || tokens[0] == '7' || tokens[0] == '8' || tokens[0] == '9' || tokens[0] == 'a'){
				if( tokens[0] == '1'){
					var text = "The new price is " + tokens[1] + "credits.<br/> Do you still want to open the box with this price?<br/><br/><input type='button' value='Yes' onclick='javascript:boxBuy("+g+","+tokens[1]+",-1,document.getElementById(\"buyButton"+g+"\"));'/>&nbsp;<input type='button' value='No'  onclick='javascript:hideAllBubblePopups();'/>"
					document.getElementById("boxPrice").innerHTML = tokens[1];
					button.disabled=false;
					enableButtonItem();
				}
				if( tokens[0] == '2'){
					var text = "You don't have enough credits to open this box.<br/>Do you want to buy more credits?<br/><br/><input type='button' value='Yes' onclick=\"document.location.href='/do/credits/buy'\"/>&nbsp;<input type='button' value='No'  onclick='javascript:hideAllBubblePopups();'/>";
					button.disabled=false;
					enableButtonItem();
				}
				if( tokens[0] == '3'){
					var text = "The game is over.";
					button.disabled=true;
				}
				if( tokens[0] == '4'){
					var text = "If you want to open boxes you neeed to be registered.<br/>Do you want to register in Instantri.ch now?<br/><br/><input type='button' value='Yes' style='width:40px;' onclick='document.location.href=\"/register\"'/>&nbsp;<input type='button' value='No' style='width:40px;' onclick='javascript:hideAllBubblePopups();'/>";
					button.disabled=false;
					enableButtonItem();
				}
				if( tokens[0] == '5'){
					var text = "You cannot open a box when<br/>you found the prize position.";
					button.disabled=true;
				}
				if( tokens[0] == '6'){
					var text = "This game is only for beginners.";
					button.disabled=true;
				}
				if( tokens[0] == '7'){
					var text = "This box has been already opened.";
					button.disabled=false;
					enableButtonItem();
				}
				if( tokens[0] == '8'){
					var text = "In free games you can<br/>only open one box per game.";
					button.disabled=false;
					enableButtonItem();
				}
				if( tokens[0] == '9'){
					var text = "You cannot participate in free games<br/>if your email address is not validated.";
					button.disabled=false;
					enableButtonItem();
				}
				if( tokens[0] == 'a'){
					var text = "Your user is marked as fraudulent.<br/>You cannot open boxes in any game.";
					enableButtonItem();
				}
				
				var options =	{ 
					themePath : '/jquerybubblepopup-theme/',
					mouseOver: 'hide',
					innerHtml:  text
				};
				
				
				if( b == -1){
					$('#buyButton').CreateBubblePopup(options);
					$('#buyButton').ShowBubblePopup();
					$('#buyButton').FreezeBubblePopup();
					if( tokens[0] == '3' || tokens[0] == '5' || tokens[0] == '6' || tokens[0] == '7' || tokens[0] == '8' || tokens[0] == '9' || tokens[0] == 'a'){
						setTimeout("$('#buyButton').HideBubblePopup()", 1800);
					}
				} else{
					$('#b'+b).CreateBubblePopup(options);
					$('#b'+b).ShowBubblePopup();
					$('#b'+b).FreezeBubblePopup();
					if( tokens[0] == '3' || tokens[0] == '5' || tokens[0] == '6' || tokens[0] == '7' || tokens[0] == '8' || tokens[0] == '9' || tokens[0] == 'a'){
						setTimeout("$('#b"+b+"').HideBubblePopup()", 1800);
					}
				}
				
				opening = false;
			} else{
				sessionTimeOutMinutes = 59;
			}
	 	});
	 }
}

function disableButtonItem(){
	var elm=document.getElementById('buyButton');
	if( elm != null){
		elm.src="/img/btn_destapar_aleatoria_disabled.png";
	}
	
}

function enableButtonItem(){
	var elm=document.getElementById('buyButton');
	if( elm != null){
		elm.src="/img/btn_destapar_aleatoria.png";
	}
}


var segundosItem = 0; 
var minutosItem = 0;
var cronoIdItem = 0;

function initCronoItem( s) {
	segundosItem = s % 60;
	minutosItem = Math.floor(s /60);
	var valorCrono = "";
	valorCrono = (minutosItem < 10) ? "0" + minutosItem : minutosItem;
	valorCrono += (segundosItem < 10) ? ":0" + segundosItem : ":" + segundosItem;
	var crono = document.getElementById("crono");
	if( crono != null){
		crono.innerHTML = valorCrono;
	}
}

function stopCronoItem(){
  clearTimeout(cronoIdItem);
}

function showCronoItem() {
	segundosItem--;
	if ( segundosItem < 0 ) {
		segundosItem = 59;
		minutosItem--;
		if ( minutosItem < 0 ) {
			stopCrono;
			return true;
		}
	}

	var valorCrono = "";
	valorCrono = (minutosItem < 10) ? "0" + minutosItem : minutosItem;
	valorCrono += (segundosItem < 10) ? ":0" + segundosItem : ":" + segundosItem;
	var crono = document.getElementById("crono");
	if( crono != null){
  		crono.innerHTML = valorCrono;
  		cronoIdItem =setTimeout("showCronoItem()", 1000);
		return true;
	}
}


var sessionTimeOutMinutes=59;

function checkSessionTimeOut(){
	sessionTimeOutMinutes--;

	if( sessionTimeOutMinutes <=0){
		showSessionThickbox();
	} else{
		setTimeout("checkSessionTimeOut()", 60000);
	}
}



//Buy


function validatePromoCode(code){
	$.post("https://www.instantri.ch/do/credits/validate", { code:code}, function(data){
		var tokens = data.split("#");
		var result = tokens[0];
		var text = tokens[1]; 
		
		if( result != 0){
			document.getElementById("promoCodeId").innerHTML = "<span style='color:green'/>Promotion <strong>" + tokens[1] + "</strong> activated</span><br/><span style='color:#666;font-size:10px;font-weight:normal;'/>(You cant' use any other promotion now)</span>";
			document.getElementById("promoCodeTypeId").innerHTML = result;
			changeEuros();
		} else{
			document.getElementById("promoCodeId").innerHTML = text;
			document.getElementById("promoCodeTypeId").innerHTML = result;
			changeEuros();
		}
				
 	});
}



var IR_Animation = {
		
		mainTL:null,
		
		init:function(){
			IR_Animation.relocate();
			IR_Animation.buildTimelines();

			$(window).resize(function(){
			    IR_Animation.relocate();
			});

			$('#IRanimation .buttonHolder button').click(function(){
				IR_Animation.close();
			})
		},

		buildTimelines:function(){

			var shakeTL = new TimelineMax();
			shakeTL.to("#IRanimation .box", 2, {rotation:"10deg", x:30, ease:Quad.easeInOut})
					.to("#IRanimation .box", 1, {rotation:"-8deg", x:-25, ease:Quad.easeInOut})
					.to("#IRanimation .box", 1, {rotation:"6deg", x:20, ease:Quad.easeInOut})
					.to("#IRanimation .box", 1, {rotation:"-4deg", x:-15, ease:Quad.easeInOut})
					.to("#IRanimation .box", 1, {rotation:"2deg", x:10, ease:Quad.easeInOut})
					.to("#IRanimation .box", 1, {rotation:"0deg", x:0, ease:Quad.easeInOut})
					;
			shakeTL.stop();
				
			var openTL = new TimelineMax();
			openTL.to("#IRanimation .boxPart.top", 2, {y:"-150",alpha:0,rotation:"20deg", x:30, ease:Quint.easeInOut})
					.to("#IRanimation .prizeItem", 3, {y:"-170", ease:Quint.easeOut}, "-=1.3")
					.to("#IRanimation .prizeItem", 1.5, {scale:2, ease:Quad.easeOut}, "-=2.8")
					.to("#IRanimation .box", 3, {y:"10",scale:0.65, ease:Quint.easeOut}, "-=2.8")
					.from("#IRanimation .gradientBG", 4, {alpha:0, ease:Quint.easeOut}, "-=3")
					.from("#IRanimation .raysBG", 4, {alpha:0, ease:Quint.easeOut}, "-=4")
					;
			openTL.stop();

			var raysTL = new TimelineMax({repeat:-1});
			raysTL.to("#IRanimation .raysBG img", 10, {rotation:360, ease:Linear.easeNone})
					.to("#IRanimation .raysBG img", 2.5, {alpha:.5, scale:.8, ease:Quad.easeInOut}, 0)
					.to("#IRanimation .raysBG img", 2.5, {alpha:1, scale:1, ease:Quad.easeInOut}, 2.5)
					.to("#IRanimation .raysBG img", 2.5, {alpha:.5, scale:.8, ease:Quad.easeInOut}, 5)
					.to("#IRanimation .raysBG img", 2.5, {alpha:1, scale:1, ease:Quad.easeInOut}, 7.5)
					;
				
			var textATL = new TimelineMax();
				textATL.from("#IRanimation .textA.congrats", 2, {alpha:0, y:"100", scale:.85, ease:Back.easeOut})
						.from("#IRanimation .textA.userName", 4, {alpha:0, scale:.85, ease:Back.easeOut}, "-=1")
			textATL.stop();

			var textBTL = new TimelineMax();
				textBTL.from("#IRanimation .textB", 3, {alpha:0, scale:.75, ease:Elastic.easeOut});
			textBTL.stop();


			mainTL = new TimelineMax();

			mainTL.fromTo("#IRanimation", .5, {autoAlpha:0},{autoAlpha:1, ease:Quad.easeOut}, 0)
					.from("#IRanimation .box", .5, {autoAlpha:0, scale:.5, ease:Quad.easeOut}, .5)
					.to(shakeTL, .7, {progress:1}, "-=0")
					.to(openTL, 3, {progress:1}, "-=0")
					.to(textATL, 2, {progress:1}, "-=2.5")
					.to(textBTL, 3, {progress:1}, "-=2")
					.from($('#IRanimation .textC'), 1, {alpha:0}, "-=2")
					.from($('#IRanimation .buttonHolder'), .5, {scale:0, ease:Back.easeOut}, "-=1")

					;
			
			mainTL.stop();
					
		},
		play:function(nickname, imgURL, textDesc, duration)
		{
			if (typeof(duration)==='undefined') duration = mainTL.totalDuration();

			$('#IRanimation .textA.userName').html(nickname);
			$('#IRanimation .prizeItem').attr('src', imgURL);
			$('#IRanimation .textC p').html(textDesc);

			TweenMax.fromTo(mainTL, parseFloat(duration), {progress:0},{progress:1, ease:Linear.easeNone});
		},
		close:function(duration){

			if (typeof(duration)==='undefined') duration = .5;
			TweenMax.to("#IRanimation", 1, {autoAlpha:0, ease:Quad.easeOut});
		},
		relocate:function(){
			var initHeight = parseInt($('#IRanimation .animWrapper').css('height'));
			var topPos = Math.max((window.innerHeight-initHeight)/2,0)

			$('#IRanimation .animWrapper').css('top',topPos);
		}

	}



var IR_AnimationB = {
	
	mainTL:null,
	
	init:function(){
		IR_AnimationB.relocate();
		IR_AnimationB.buildTimelines();
		initConfetti();

		$('#IRanimationB .buttonHolder button').click(function(){
			IR_AnimationB.close();
		});
	},

	buildTimelines:function(){

		var openTL = new TimelineMax();
			openTL.from("#IRanimationB .prizeItem", 1.5, {alpha:0, y:"20", scale:.5, ease:Quad.easeOut}, "-=2.8")
				.from("#IRanimationA .gradientBG", 4, {alpha:0, ease:Quint.easeOut}, "-=3")
				.from("#IRanimationA .raysBG", 4, {alpha:0, ease:Quint.easeOut}, "-=4")
				;
		openTL.stop();

		var raysTL = new TimelineMax({repeat:-1});
		raysTL.to("#IRanimationB .raysBG img", 10, {rotation:360, ease:Linear.easeNone})
				.to("#IRanimationB .raysBG img", 2.5, {alpha:.5, scale:.8, ease:Quad.easeInOut}, 0)
				.to("#IRanimationB .raysBG img", 2.5, {alpha:1, scale:1, ease:Quad.easeInOut}, 2.5)
				.to("#IRanimationB .raysBG img", 2.5, {alpha:.5, scale:.8, ease:Quad.easeInOut}, 5)
				.to("#IRanimationB .raysBG img", 2.5, {alpha:1, scale:1, ease:Quad.easeInOut}, 7.5)
				;
			
		var textATL = new TimelineMax();
			textATL.from("#IRanimationB .textA.congrats", 2, {alpha:0, y:"100", scale:.85, ease:Back.easeOut})
					.from("#IRanimationB .textA.userName", 4, {alpha:0, scale:.85, ease:Back.easeOut}, "-=1")
		textATL.stop();

		var textBTL = new TimelineMax();
			textBTL.from("#IRanimationB .textB", 3, {alpha:0, scale:.75, ease:Elastic.easeOut});
		textBTL.stop();


		this.mainTL = new TimelineMax();

		this.mainTL.fromTo("#IRanimationB", .5, {autoAlpha:0},{autoAlpha:1, ease:Quad.easeOut}, 0)
				.to(openTL, 3, {progress:1}, 0)
				.to(textATL, 2, {progress:1}, "-=2.5")
				.to(textATL, 2, {progress:1}, "-=2.5")
				.to(textBTL, 3, {progress:1}, "-=2")
				.from($('#IRanimationB .textC'), 1, {alpha:0}, "-=2")
				.from($('#IRanimationB .buttonHolder'), .5, {scale:0, ease:Back.easeOut}, "-=1")

				;
		
		this.mainTL.stop();
				
	},
	play:function(nickname, imgURL, textDesc, duration)
	{
		if (typeof(duration)==='undefined' || typeof(duration)===null) duration = this.mainTL.totalDuration();

		$(window).resize(IR_AnimationB.relocate);

		$('#IRanimationB .textA.userName').html(nickname);
		$('#IRanimationB .prizeItem').attr('src', imgURL);
		$('#IRanimationB .textC p').html(textDesc);

		TweenMax.fromTo(this.mainTL, parseFloat(duration), {progress:0},{progress:1, ease:Linear.easeNone});

		StartConfetti();
	},
	close:function(duration){
		if (typeof(duration)==='undefined') duration = .5;
		TweenMax.to("#IRanimationB", 1, {autoAlpha:0, ease:Quad.easeOut,onComplete:this.kill});
	},
	kill:function(){
		$(window).off("resize", IR_AnimationB.relocate);

		StopConfetti();
	},
	relocate:function(){
		var initHeight = parseInt($('#IRanimationB .animWrapper').css('height'));
		var topPos = Math.max((window.innerHeight-initHeight)/2,0)

		$('#IRanimationB .animWrapper').css('top',topPos);
	}

}





var IR_AnimationC = {
	
	mainTL:null,
	
	init:function(){
		IR_AnimationC.relocate();
		IR_AnimationC.buildTimelines();
		
		$('#IRanimationC .buttonHolder button').click(function(){
			IR_AnimationC.close();
		});
	},

	buildTimelines:function(){


		var tokenAppearing = new TimelineMax();
			tokenAppearing.from("#IRanimationC .credit", 2, {alpha:0, y:"300", scale:.5,rotationX:200, ease:Quint.easeOut})
						
				;
			tokenAppearing.stop();

		

		var starshineTL = new TimelineMax();
			starshineTL.staggerFromTo("#IRanimationC .credit .star", 1, {scale:.5}, {scale:3, alpha:0, force3D:false, ease:Quad.easeOut},.2)
				;
			starshineTL.stop();

			
		
		this.mainTL = new TimelineMax();

		this.mainTL.fromTo("#IRanimationC", .5, {autoAlpha:0},{autoAlpha:1, ease:Quad.easeOut}, 0)
				.to(tokenAppearing, 2, {progress:1}, 0)
				.to(starshineTL, starshineTL.totalDuration(), {progress:1}, .5)				
				.from($('#IRanimationC .textB'), 1, {y:"50", alpha:0}, "-=2")
				.from($('#IRanimationC .textE'), 1, {y:"50", alpha:0}, "-=1.8")
				
				.to($('#IRanimationC .textE'), .5, {y:"-10", alpha:0}, "+=.5")
				.to($('#IRanimationC .textB'), .5, {y:"-10", alpha:0}, "-=.5")
				.to($('#IRanimationC .credit'), .5, {y:"-15", scale:1.1, alpha:0}, "-=.5")
				.to($('#IRanimationC'), .7, {autoAlpha:0, ease:Quad.easeOut}, "+=0")
				;
		
		this.mainTL.stop();
				
	},
	play:function(credText, imgURL, duration)
	{
		if (typeof(duration)==='undefined') duration = this.mainTL.totalDuration();

		$(window).resize(IR_AnimationC.relocate);

		$('#IRanimationC .textE.prize').html(credText+" credits!");
		$('#IRanimationC .token').attr('src', imgURL);

		TweenMax.fromTo(this.mainTL, parseFloat(duration), {progress:0},{progress:1, ease:Linear.easeNone, onComplete:this.kill});
	},
	close:function(){
		$(window).off("resize", IR_AnimationC.relocate);
	},
	relocate:function(){
		var initHeight = parseInt($('#IRanimationC .animWrapper').css('height'));
		var topPos = Math.max((window.innerHeight-initHeight)/2,0)

		$('#IRanimationC .animWrapper').css('top',topPos);
	}

}


