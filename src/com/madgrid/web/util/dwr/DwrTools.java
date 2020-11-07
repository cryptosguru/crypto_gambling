package com.madgrid.web.util.dwr;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.ojb.broker.query.Criteria;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.proxy.dwr.Util;

import com.madgrid.dao.BoxDAO;
import com.madgrid.dao.GridDAO;
import com.madgrid.dao.GridHistoricDAO;
import com.madgrid.dao.ItemDAO;
import com.madgrid.dao.ParameterDAO;
import com.madgrid.dao.PromotionDAO;
import com.madgrid.dao.UserDAO;
import com.madgrid.dao.UserHistoricDAO;
import com.madgrid.dao.WinDAO;
import com.madgrid.model.Box;
import com.madgrid.model.Grid;
import com.madgrid.model.GridHistoric;
import com.madgrid.model.Item;
import com.madgrid.model.Parameter;
import com.madgrid.model.Promotion;
import com.madgrid.model.User;
import com.madgrid.model.UserHistoric;
import com.madgrid.model.Win;
import com.madgrid.web.util.Mail;
import com.madgrid.web.util.UserSession;
import com.madgrid.web.util.Utils;
import com.madgrid.web.util.mail.UserWinMailObject;
import com.madgrid.web.util.mail.ReturnCreditsMailObject;
import com.madgrid.web.util.mail.ReturnCreditsPromotionMailObject;

public class DwrTools {
	
	 private static DwrTools INSTANCE;
	 private static Set<Integer> openingBoxSet = new HashSet<Integer>();
	 
	 public static Set<String> fraudulentEmails = new HashSet<String>();
	 static{
		 	fraudulentEmails.add("dropjar.com");
			fraudulentEmails.add("spikio.com");
			fraudulentEmails.add("paplease.com");
			fraudulentEmails.add("mailtothis.com");
			fraudulentEmails.add("monumentmail.com");
			fraudulentEmails.add("streetwisemail.com");
			fraudulentEmails.add("hmamail.com");
			fraudulentEmails.add("15qm.com");
			fraudulentEmails.add("hiru-dea.com");
			fraudulentEmails.add("tryalert.com");
			fraudulentEmails.add("getairmail.com");
			fraudulentEmails.add("grandmamail.com");
			fraudulentEmails.add("maildrop.cc");
			fraudulentEmails.add("imgof.com");
			fraudulentEmails.add("superrito.com");
			fraudulentEmails.add("anonmails.de");
			fraudulentEmails.add("soisz.com");
			fraudulentEmails.add("libertysurf.fr");
			fraudulentEmails.add("leeching.net");
			fraudulentEmails.add("extremail.ru");
			fraudulentEmails.add("trbvn.com");
			fraudulentEmails.add("mswork.ru");
			fraudulentEmails.add("gustr.com");
			fraudulentEmails.add("opayq.com");
			fraudulentEmails.add("abyssmail.com");
			fraudulentEmails.add("boximail.com");
			fraudulentEmails.add("eelmail.com");
			fraudulentEmails.add("laoeq.com");
			fraudulentEmails.add("clrmail.com");
			fraudulentEmails.add("anappthat.com");
			fraudulentEmails.add("zetmail.com");
			fraudulentEmails.add("tafmail.com");
			fraudulentEmails.add("grandmasmail.com");
			fraudulentEmails.add("t.pl");
			fraudulentEmails.add("uk.pl");
			fraudulentEmails.add("alivance.com");
			fraudulentEmails.add("walkmail.net");
			fraudulentEmails.add("lackmail.net");
			fraudulentEmails.add("rhyta.com");
			fraudulentEmails.add("trbvm.com");
			fraudulentEmails.add("hideme.be");
			fraudulentEmails.add("fleckens.hu");
			fraudulentEmails.add("einrot.com");
			fraudulentEmails.add("mailpick.biz");
			fraudulentEmails.add("armyspy.com");
			fraudulentEmails.add("jourrapide.com");
			fraudulentEmails.add("inboxalias.com");
			fraudulentEmails.add("6paq.com");
			fraudulentEmails.add("advantimo.com");
			fraudulentEmails.add("mailismagic.com");
			fraudulentEmails.add("reallymymail.com");
			fraudulentEmails.add("cuvox.de");
			fraudulentEmails.add("thecloudindex.com");
			fraudulentEmails.add("daintly.com");
			fraudulentEmails.add("droplar.com");
			fraudulentEmails.add("sharklasers.com");
			fraudulentEmails.add("spam4.me");
			fraudulentEmails.add("grr.la");
			fraudulentEmails.add("drdrb.net");
			fraudulentEmails.add("pwrby.com");
			fraudulentEmails.add("fammix.com");
			fraudulentEmails.add("toomail.biz");
			fraudulentEmails.add("draw4e");
			fraudulentEmails.add("0-mail.com");
			fraudulentEmails.add("0815.ru");
			fraudulentEmails.add("0clickemail.com");
			fraudulentEmails.add("0wnd.net");
			fraudulentEmails.add("0wnd.org");
			fraudulentEmails.add("10minutemail.com");
			fraudulentEmails.add("20minutemail.com");
			fraudulentEmails.add("2prong.com");
			fraudulentEmails.add("30minutemail.com");
			fraudulentEmails.add("3d-painting.com");
			fraudulentEmails.add("4warding.com");
			fraudulentEmails.add("4warding.net");
			fraudulentEmails.add("4warding.org");
			fraudulentEmails.add("60minutemail.com");
			fraudulentEmails.add("675hosting.com");
			fraudulentEmails.add("675hosting.net");
			fraudulentEmails.add("675hosting.org");
			fraudulentEmails.add("6url.com");
			fraudulentEmails.add("75hosting.com");
			fraudulentEmails.add("75hosting.net");
			fraudulentEmails.add("75hosting.org");
			fraudulentEmails.add("7tags.com");
			fraudulentEmails.add("9ox.net");
			fraudulentEmails.add("a-bc.net");
			fraudulentEmails.add("afrobacon.com");
			fraudulentEmails.add("ajaxapp.net");
			fraudulentEmails.add("amilegit.com");
			fraudulentEmails.add("amiri.net");
			fraudulentEmails.add("amiriindustries.com");
			fraudulentEmails.add("anonbox.net");
			fraudulentEmails.add("anonymbox.com");
			fraudulentEmails.add("antichef.com");
			fraudulentEmails.add("antichef.net");
			fraudulentEmails.add("antispam.de");
			fraudulentEmails.add("baxomale.ht.cx");
			fraudulentEmails.add("beefmilk.com");
			fraudulentEmails.add("binkmail.com");
			fraudulentEmails.add("bio-muesli.net");
			fraudulentEmails.add("bobmail.info");
			fraudulentEmails.add("bodhi.lawlita.com");
			fraudulentEmails.add("bofthew.com");
			fraudulentEmails.add("brefmail.com");
			fraudulentEmails.add("broadbandninja.com");
			fraudulentEmails.add("bsnow.net");
			fraudulentEmails.add("bugmenot.com");
			fraudulentEmails.add("bumpymail.com");
			fraudulentEmails.add("casualdx.com");
			fraudulentEmails.add("centermail.com");
			fraudulentEmails.add("centermail.net");
			fraudulentEmails.add("chogmail.com");
			fraudulentEmails.add("choicemail1.com");
			fraudulentEmails.add("cool.fr.nf");
			fraudulentEmails.add("correo.blogos.net");
			fraudulentEmails.add("cosmorph.com");
			fraudulentEmails.add("courriel.fr.nf");
			fraudulentEmails.add("courrieltemporaire.com");
			fraudulentEmails.add("cubiclink.com");
			fraudulentEmails.add("curryworld.de");
			fraudulentEmails.add("cust.in");
			fraudulentEmails.add("dacoolest.com");
			fraudulentEmails.add("dandikmail.com");
			fraudulentEmails.add("dayrep.com");
			fraudulentEmails.add("deadaddress.com");
			fraudulentEmails.add("deadspam.com");
			fraudulentEmails.add("despam.it");
			fraudulentEmails.add("despammed.com");
			fraudulentEmails.add("devnullmail.com");
			fraudulentEmails.add("dfgh.net");
			fraudulentEmails.add("digitalsanctuary.com");
			fraudulentEmails.add("discardmail.com");
			fraudulentEmails.add("discardmail.de");
			fraudulentEmails.add("disposableemailaddresses:emailmiser.com");
			fraudulentEmails.add("disposableaddress.com");
			fraudulentEmails.add("vkcode.ru");
			fraudulentEmails.add("disposeamail.com");
			fraudulentEmails.add("disposemail.com");
			fraudulentEmails.add("dispostable.com");
			fraudulentEmails.add("dm.w3internet.co.ukexample.com");
			fraudulentEmails.add("dodgeit.com");
			fraudulentEmails.add("dodgit.com");
			fraudulentEmails.add("olypmall.ru");
			fraudulentEmails.add("kloap.com");
			fraudulentEmails.add("iaoss.com");
			fraudulentEmails.add("mailzi.ru");
			fraudulentEmails.add("yhg.biz");
			fraudulentEmails.add("dodgit.org");
			fraudulentEmails.add("donemail.ru");
			fraudulentEmails.add("dontreg.com");
			fraudulentEmails.add("dontsendmespam.de");
			fraudulentEmails.add("dump-email.info");
			fraudulentEmails.add("dumpandjunk.com");
			fraudulentEmails.add("dumpmail.de");
			fraudulentEmails.add("dumpyemail.com");
			fraudulentEmails.add("e4ward.com");
			fraudulentEmails.add("email60.com");
			fraudulentEmails.add("emaildienst.de");
			fraudulentEmails.add("emailias.com");
			fraudulentEmails.add("emailigo.de");
			fraudulentEmails.add("emailinfive.com");
			fraudulentEmails.add("emailmiser.com");
			fraudulentEmails.add("emailsensei.com");
			fraudulentEmails.add("emailtemporario.com.br");
			fraudulentEmails.add("emailto.de");
			fraudulentEmails.add("emailwarden.com");
			fraudulentEmails.add("emailx.at.hm");
			fraudulentEmails.add("emailxfer.com");
			fraudulentEmails.add("emz.net");
			fraudulentEmails.add("enterto.com");
			fraudulentEmails.add("ephemail.net");
			fraudulentEmails.add("etranquil.com");
			fraudulentEmails.add("etranquil.net");
			fraudulentEmails.add("etranquil.org");
			fraudulentEmails.add("explodemail.com");
			fraudulentEmails.add("fakeinbox.com");
			fraudulentEmails.add("fakeinformation.com");
			fraudulentEmails.add("fastacura.com");
			fraudulentEmails.add("fastchevy.com");
			fraudulentEmails.add("yomail.info");
			fraudulentEmails.add("fastchrysler.com");
			fraudulentEmails.add("fastkawasaki.com");
			fraudulentEmails.add("fastmazda.com");
			fraudulentEmails.add("fastmitsubishi.com");
			fraudulentEmails.add("fastnissan.com");
			fraudulentEmails.add("fastsubaru.com");
			fraudulentEmails.add("fastsuzuki.com");
			fraudulentEmails.add("fasttoyota.com");
			fraudulentEmails.add("fastyamaha.com");
			fraudulentEmails.add("filzmail.com");
			fraudulentEmails.add("fizmail.com");
			fraudulentEmails.add("fr33mail.info");
			fraudulentEmails.add("frapmail.com");
			fraudulentEmails.add("front14.org");
			fraudulentEmails.add("fux0ringduh.com");
			fraudulentEmails.add("garliclife.com");
			fraudulentEmails.add("get1mail.com");
			fraudulentEmails.add("get2mail.fr");
			fraudulentEmails.add("getonemail.com");
			fraudulentEmails.add("getonemail.net");
			fraudulentEmails.add("ghosttexter.de");
			fraudulentEmails.add("girlsundertheinfluence.com");
			fraudulentEmails.add("gishpuppy.com");
			fraudulentEmails.add("gowikibooks.com");
			fraudulentEmails.add("gowikicampus.com");
			fraudulentEmails.add("gowikicars.com");
			fraudulentEmails.add("gowikifilms.com");
			fraudulentEmails.add("gowikigames.com");
			fraudulentEmails.add("gowikimusic.com");
			fraudulentEmails.add("gowikinetwork.com");
			fraudulentEmails.add("gowikitravel.com");
			fraudulentEmails.add("gowikitv.com");
			fraudulentEmails.add("great-host.in");
			fraudulentEmails.add("greensloth.com");
			fraudulentEmails.add("gsrv.co.uk");
			fraudulentEmails.add("guerillamail.biz");
			fraudulentEmails.add("guerillamail.com");
			fraudulentEmails.add("guerillamail.net");
			fraudulentEmails.add("guerillamail.org");
			fraudulentEmails.add("guerrillamail.biz");
			fraudulentEmails.add("guerrillamail.com");
			fraudulentEmails.add("guerrillamail.de");
			fraudulentEmails.add("guerrillamail.net");
			fraudulentEmails.add("guerrillamail.org");
			fraudulentEmails.add("guerrillamailblock.com");
			fraudulentEmails.add("h8s.org");
			fraudulentEmails.add("haltospam.com");
			fraudulentEmails.add("hatespam.org");
			fraudulentEmails.add("hidemail.de");
			fraudulentEmails.add("hochsitze.com");
			fraudulentEmails.add("hotpop.com");
			fraudulentEmails.add("hulapla.de");
			fraudulentEmails.add("ieatspam.eu");
			fraudulentEmails.add("ieatspam.info");
			fraudulentEmails.add("ihateyoualot.info");
			fraudulentEmails.add("iheartspam.org");
			fraudulentEmails.add("imails.info");
			fraudulentEmails.add("inboxclean.com");
			fraudulentEmails.add("inboxclean.org");
			fraudulentEmails.add("incognitomail.com");
			fraudulentEmails.add("incognitomail.net");
			fraudulentEmails.add("incognitomail.org");
			fraudulentEmails.add("insorg-mail.info");
			fraudulentEmails.add("ipoo.org");
			fraudulentEmails.add("irish2me.com");
			fraudulentEmails.add("iwi.net");
			fraudulentEmails.add("jetable.com");
			fraudulentEmails.add("jetable.fr.nf");
			fraudulentEmails.add("jetable.net");
			fraudulentEmails.add("jetable.org");
			fraudulentEmails.add("jnxjn.com");
			fraudulentEmails.add("junk1e.com");
			fraudulentEmails.add("kasmail.com");
			fraudulentEmails.add("kaspop.com");
			fraudulentEmails.add("keepmymail.com");
			fraudulentEmails.add("killmail.com");
			fraudulentEmails.add("killmail.net");
			fraudulentEmails.add("kir.ch.tc");
			fraudulentEmails.add("klassmaster.com");
			fraudulentEmails.add("klassmaster.net");
			fraudulentEmails.add("klzlk.com");
			fraudulentEmails.add("kulturbetrieb.info");
			fraudulentEmails.add("kurzepost.de");
			fraudulentEmails.add("letthemeatspam.com");
			fraudulentEmails.add("lhsdv.com");
			fraudulentEmails.add("lifebyfood.com");
			fraudulentEmails.add("link2mail.net");
			fraudulentEmails.add("litedrop.com");
			fraudulentEmails.add("lol.ovpn.to");
			fraudulentEmails.add("lookugly.com");
			fraudulentEmails.add("lopl.co.cc");
			fraudulentEmails.add("lortemail.dk");
			fraudulentEmails.add("lr78.com");
			fraudulentEmails.add("m4ilweb.info");
			fraudulentEmails.add("maboard.com");
			fraudulentEmails.add("mail-temporaire.fr");
			fraudulentEmails.add("mail.by");
			fraudulentEmails.add("mail.mezimages.net");
			fraudulentEmails.add("mail2rss.org");
			fraudulentEmails.add("mail333.com");
			fraudulentEmails.add("mail4trash.com");
			fraudulentEmails.add("mailbidon.com");
			fraudulentEmails.add("mailblocks.com");
			fraudulentEmails.add("mailcatch.com");
			fraudulentEmails.add("maileater.com");
			fraudulentEmails.add("mailexpire.com");
			fraudulentEmails.add("mailfreeonline.com");
			fraudulentEmails.add("mailin8r.com");
			fraudulentEmails.add("mailinater.com");
			fraudulentEmails.add("mailinator.com");
			fraudulentEmails.add("mailinator.net");
			fraudulentEmails.add("mailinator2.com");
			fraudulentEmails.add("mailincubator.com");
			fraudulentEmails.add("mailme.ir");
			fraudulentEmails.add("mailme.lv");
			fraudulentEmails.add("mailmetrash.com");
			fraudulentEmails.add("mailmoat.com");
			fraudulentEmails.add("mailnator.com");
			fraudulentEmails.add("mailnesia.com");
			fraudulentEmails.add("mailnull.com");
			fraudulentEmails.add("mailshell.com");
			fraudulentEmails.add("mailsiphon.com");
			fraudulentEmails.add("mailslite.com");
			fraudulentEmails.add("mailzilla.com");
			fraudulentEmails.add("mailzilla.org");
			fraudulentEmails.add("mbx.cc");
			fraudulentEmails.add("mega.zik.dj");
			fraudulentEmails.add("meinspamschutz.de");
			fraudulentEmails.add("meltmail.com");
			fraudulentEmails.add("messagebeamer.de");
			fraudulentEmails.add("mierdamail.com");
			fraudulentEmails.add("mintemail.com");
			fraudulentEmails.add("moburl.com");
			fraudulentEmails.add("moncourrier.fr.nf");
			fraudulentEmails.add("monemail.fr.nf");
			fraudulentEmails.add("monmail.fr.nf");
			fraudulentEmails.add("msa.minsmail.com");
			fraudulentEmails.add("mt2009.com");
			fraudulentEmails.add("mx0.wwwnew.eu");
			fraudulentEmails.add("mycleaninbox.net");
			fraudulentEmails.add("mypartyclip.de");
			fraudulentEmails.add("myphantomemail.com");
			fraudulentEmails.add("myspaceinc.com");
			fraudulentEmails.add("myspaceinc.net");
			fraudulentEmails.add("myspaceinc.org");
			fraudulentEmails.add("myspacepimpedup.com");
			fraudulentEmails.add("myspamless.com");
			fraudulentEmails.add("mytrashmail.com");
			fraudulentEmails.add("neomailbox.com");
			fraudulentEmails.add("nepwk.com");
			fraudulentEmails.add("nervmich.net");
			fraudulentEmails.add("nervtmich.net");
			fraudulentEmails.add("netmails.com");
			fraudulentEmails.add("netmails.net");
			fraudulentEmails.add("netzidiot.de");
			fraudulentEmails.add("neverbox.com");
			fraudulentEmails.add("no-spam.ws");
			fraudulentEmails.add("nobulk.com");
			fraudulentEmails.add("noclickemail.com");
			fraudulentEmails.add("nogmailspam.info");
			fraudulentEmails.add("nomail.xl.cx");
			fraudulentEmails.add("nomail2me.com");
			fraudulentEmails.add("nomorespamemails.com");
			fraudulentEmails.add("nospam.ze.tc");
			fraudulentEmails.add("nospam4.us");
			fraudulentEmails.add("nospamfor.us");
			fraudulentEmails.add("nospamthanks.info");
			fraudulentEmails.add("notmailinator.com");
			fraudulentEmails.add("nowmymail.com");
			fraudulentEmails.add("nurfuerspam.de");
			fraudulentEmails.add("nus.edu.sg");
			fraudulentEmails.add("nwldx.com");
			fraudulentEmails.add("objectmail.com");
			fraudulentEmails.add("obobbo.com");
			fraudulentEmails.add("oneoffemail.com");
			fraudulentEmails.add("onewaymail.com");
			fraudulentEmails.add("online.ms");
			fraudulentEmails.add("oopi.org");
			fraudulentEmails.add("ordinaryamerican.net");
			fraudulentEmails.add("otherinbox.com");
			fraudulentEmails.add("temp-mail.org");
			fraudulentEmails.add("ourklips.com");
			fraudulentEmails.add("outlawspam.com");
			fraudulentEmails.add("bigprofessor.so");
			fraudulentEmails.add("ovpn.to");
			fraudulentEmails.add("owlpic.com");
			fraudulentEmails.add("pancakemail.com");
			fraudulentEmails.add("pimpedupmyspace.com");
			fraudulentEmails.add("pjjkp.com");
			fraudulentEmails.add("politikerclub.de");
			fraudulentEmails.add("poofy.org");
			fraudulentEmails.add("pookmail.com");
			fraudulentEmails.add("privacy.net");
			fraudulentEmails.add("proxymail.eu");
			fraudulentEmails.add("prtnx.com");
			fraudulentEmails.add("punkass.com");
			fraudulentEmails.add("putthisinyourspamdatabase.com");
			fraudulentEmails.add("quickinbox.com");
			fraudulentEmails.add("rcpt.at");
			fraudulentEmails.add("recode.me");
			fraudulentEmails.add("recursor.net");
			fraudulentEmails.add("regbypass.com");
			fraudulentEmails.add("regbypass.comsafe-mail.net");
			fraudulentEmails.add("rejectmail.com");
			fraudulentEmails.add("rklips.com");
			fraudulentEmails.add("rmqkr.net");
			fraudulentEmails.add("rppkn.com");
			fraudulentEmails.add("rtrtr.com");
			fraudulentEmails.add("s0ny.net");
			fraudulentEmails.add("safe-mail.net");
			fraudulentEmails.add("safersignup.de");
			fraudulentEmails.add("safetymail.info");
			fraudulentEmails.add("safetypost.de");
			fraudulentEmails.add("sandelf.de");
			fraudulentEmails.add("saynotospams.com");
			fraudulentEmails.add("selfdestructingmail.com");
			fraudulentEmails.add("sendspamhere.com");
			fraudulentEmails.add("shiftmail.com");
			fraudulentEmails.add("shitmail.me");
			fraudulentEmails.add("shortmail.net");
			fraudulentEmails.add("sibmail.com");
			fraudulentEmails.add("skeefmail.com");
			fraudulentEmails.add("slaskpost.se");
			fraudulentEmails.add("slopsbox.com");
			fraudulentEmails.add("smellfear.com");
			fraudulentEmails.add("snakemail.com");
			fraudulentEmails.add("sneakemail.com");
			fraudulentEmails.add("sofimail.com");
			fraudulentEmails.add("sofort-mail.de");
			fraudulentEmails.add("sogetthis.com");
			fraudulentEmails.add("soodonims.com");
			fraudulentEmails.add("spam.la");
			fraudulentEmails.add("spam.su");
			fraudulentEmails.add("spamavert.com");
			fraudulentEmails.add("spambob.com");
			fraudulentEmails.add("spambob.net");
			fraudulentEmails.add("spambob.org");
			fraudulentEmails.add("spambog.com");
			fraudulentEmails.add("spambog.de");
			fraudulentEmails.add("spambog.ru");
			fraudulentEmails.add("spambox.info");
			fraudulentEmails.add("spambox.irishspringrealty.com");
			fraudulentEmails.add("spambox.us");
			fraudulentEmails.add("spamcannon.com");
			fraudulentEmails.add("spamcannon.net");
			fraudulentEmails.add("spamcero.com");
			fraudulentEmails.add("spamcon.org");
			fraudulentEmails.add("spamcorptastic.com");
			fraudulentEmails.add("spamcowboy.com");
			fraudulentEmails.add("spamcowboy.net");
			fraudulentEmails.add("spamcowboy.org");
			fraudulentEmails.add("spamday.com");
			fraudulentEmails.add("spamex.com");
			fraudulentEmails.add("spamfree24.com");
			fraudulentEmails.add("spamfree24.de");
			fraudulentEmails.add("spamfree24.eu");
			fraudulentEmails.add("spamfree24.info");
			fraudulentEmails.add("spamfree24.net");
			fraudulentEmails.add("spamfree24.org");
			fraudulentEmails.add("spamgourmet.com");
			fraudulentEmails.add("spamgourmet.net");
			fraudulentEmails.add("spamgourmet.org");
			fraudulentEmails.add("SpamHereLots.com");
			fraudulentEmails.add("SpamHerePlease.com");
			fraudulentEmails.add("spamhole.com");
			fraudulentEmails.add("spamify.com");
			fraudulentEmails.add("spaminator.de");
			fraudulentEmails.add("spamkill.info");
			fraudulentEmails.add("spaml.com");
			fraudulentEmails.add("spaml.de");
			fraudulentEmails.add("spammotel.com");
			fraudulentEmails.add("spamobox.com");
			fraudulentEmails.add("spamoff.de");
			fraudulentEmails.add("spamslicer.com");
			fraudulentEmails.add("spamspot.com");
			fraudulentEmails.add("spamthis.co.uk");
			fraudulentEmails.add("spamthisplease.com");
			fraudulentEmails.add("spamtrail.com");
			fraudulentEmails.add("speed.1s.fr");
			fraudulentEmails.add("supergreatmail.com");
			fraudulentEmails.add("supermailer.jp");
			fraudulentEmails.add("suremail.info");
			fraudulentEmails.add("teewars.org");
			fraudulentEmails.add("teleworm.com");
			fraudulentEmails.add("tempalias.com");
			fraudulentEmails.add("tempe-mail.com");
			fraudulentEmails.add("tempemail.biz");
			fraudulentEmails.add("tempemail.com");
			fraudulentEmails.add("tempeMail.net");
			fraudulentEmails.add("tempinbox.co.uk");
			fraudulentEmails.add("tempinbox.com");
			fraudulentEmails.add("tempmail.it");
			fraudulentEmails.add("tempmail.de");
			fraudulentEmails.add("tempmailer.de");
			fraudulentEmails.add("tempmail2.com");
			fraudulentEmails.add("tempomail.fr");
			fraudulentEmails.add("temporarily.de");
			fraudulentEmails.add("temporarioemail.com.br");
			fraudulentEmails.add("temporaryemail.net");
			fraudulentEmails.add("temporaryforwarding.com");
			fraudulentEmails.add("temporaryinbox.com");
			fraudulentEmails.add("thanksnospam.info");
			fraudulentEmails.add("thankyou2010.com");
			fraudulentEmails.add("thisisnotmyrealemail.com");
			fraudulentEmails.add("throwawayemailaddress.com");
			fraudulentEmails.add("tilien.com");
			fraudulentEmails.add("tmailinator.com");
			fraudulentEmails.add("tradermail.info");
			fraudulentEmails.add("trash-amil.com");
			fraudulentEmails.add("trash-mail.at");
			fraudulentEmails.add("trash-mail.com");
			fraudulentEmails.add("trash-mail.de");
			fraudulentEmails.add("trash2009.com");
			fraudulentEmails.add("trashemail.de");
			fraudulentEmails.add("trashmail.at");
			fraudulentEmails.add("trashmail.com");
			fraudulentEmails.add("trashmail.de");
			fraudulentEmails.add("trashmail.me");
			fraudulentEmails.add("trashmail.net");
			fraudulentEmails.add("trashmail.org");
			fraudulentEmails.add("trashmail.ws");
			fraudulentEmails.add("trashmailer.com");
			fraudulentEmails.add("trashymail.com");
			fraudulentEmails.add("trashymail.net");
			fraudulentEmails.add("trillianpro.com");
			fraudulentEmails.add("turual.com");
			fraudulentEmails.add("twinmail.de");
			fraudulentEmails.add("tyldd.com");
			fraudulentEmails.add("uggsrock.com");
			fraudulentEmails.add("upliftnow.com");
			fraudulentEmails.add("uplipht.com");
			fraudulentEmails.add("venompen.com");
			fraudulentEmails.add("veryrealemail.com");
			fraudulentEmails.add("viditag.com");
			fraudulentEmails.add("viewcastmedia.com");
			fraudulentEmails.add("viewcastmedia.net");
			fraudulentEmails.add("viewcastmedia.org");
			fraudulentEmails.add("webm4il.info");
			fraudulentEmails.add("wegwerfadresse.de");
			fraudulentEmails.add("wegwerfemail.de");
			fraudulentEmails.add("wegwerfmail.de");
			fraudulentEmails.add("wegwerfmail.net");
			fraudulentEmails.add("wegwerfmail.org");
			fraudulentEmails.add("wetrainbayarea.com");
			fraudulentEmails.add("wetrainbayarea.org");
			fraudulentEmails.add("wh4f.org");
			fraudulentEmails.add("whyspam.me");
			fraudulentEmails.add("willselfdestruct.com");
			fraudulentEmails.add("winemaven.info");
			fraudulentEmails.add("wronghead.com");
			fraudulentEmails.add("wuzup.net");
			fraudulentEmails.add("wuzupmail.net");
			fraudulentEmails.add("wwwnew.eu");
			fraudulentEmails.add("xagloo.com");
			fraudulentEmails.add("xemaps.com");
			fraudulentEmails.add("xents.com");
			fraudulentEmails.add("xmaily.com");
			fraudulentEmails.add("xoxy.net");
			fraudulentEmails.add("yep.it");
			fraudulentEmails.add("yogamaven.com");
			fraudulentEmails.add("yopmail.com");
			fraudulentEmails.add("yopmail.fr");
			fraudulentEmails.add("yopmail.net");
			fraudulentEmails.add("ypmail.webarnak.fr.eu.org");
			fraudulentEmails.add("yuurok.com");
			fraudulentEmails.add("zehnminutenmail.de");
			fraudulentEmails.add("zippymail.info");
			fraudulentEmails.add("zoaxe.com");
			fraudulentEmails.add("zoemail.org");
			
	 }
	
	 
	 public static synchronized DwrTools getInstance() {
		 if (INSTANCE == null) {
			 INSTANCE = new DwrTools();
			 System.setProperty("networkaddress.cache.ttl", "500");
	         
		 }
		 return INSTANCE;
	   }
	
	public void reverseAjaxIndex () throws Exception{
	   ReverseAjaxThread thread = ReverseAjaxThread.getInstance();
	   thread.addScriptSessionIndex(WebContextFactory.get().getHttpServletRequest(), WebContextFactory.get().getScriptSession());
	   UserSession userSession = (UserSession)WebContextFactory.get().getHttpServletRequest().getSession().getAttribute( "userSession");
	   User user = null;
	   
	   if( userSession != null){
		   user = userSession.getUser();
	   }
	   
	   Util utilUser = new Util(WebContextFactory.get().getScriptSession());

	   List<GridData> gridDataList = generateGridTable( user);
	   
	   String result  = "<div class='filaProductos'>";
	   
	   for(int i = 0;i<12;i++){
		   result = result + "<div id='mtr"+i+"'></div>";
		   if( i==2){
			   result = result + "</div>";
			   if( userSession == null){
				   result = result + "<div class='destacadoRegistro'>";
				   result = result + "<p class='primero'>Still not an user? With Instantri.ch you can win <strong>lots of prizes</strong> instantly just opening boxes</p>";
				   result = result + "<p class='segundo'>After our 1 minute registration you can start playing immediately in our free games or buy credits to play in any game.</p>";
				   result = result + "<a href='/register' class='registrate'><img src='/img/btn_registrate.png' alt='Sing up' /></a>";
				   result = result + "</div>";
			   }
			   result = result  + "<div class='filaProductos'>";
		   }
		   if( i==5){
			   result = result + "</div>";
			   result = result  + "<div class='filaProductos'>";
		   }
		   if( i==8){
			   result = result + "</div>";
			   result = result  + "<div class='filaProductos'>";
		   }
	   }
	   
	   utilUser.setValue("pagina", result);

	   Thread.sleep(200);
	   
	   int i = 0;
	   for( GridData gridData: gridDataList){
		   utilUser.setValue("mtr"+i+"", gridData.getGridHtml());
		   
		   if( gridData.getIsInPartialWin()){
			   utilUser.addFunctionCall("stopCrono", gridData.getId());
			   utilUser.addFunctionCall("initCrono", gridData.getId(), gridData.getPartialWinSeconds());
			   utilUser.addFunctionCall("showCrono", gridData.getId());  
		   }
		   
		   i++;
	   }
	   utilUser.addFunctionCall("reloadCluetip");
   }
	
	public void reverseAjaxItem (Integer id) throws Exception{
		
		UserSession userSession = (UserSession)WebContextFactory.get().getHttpServletRequest().getSession().getAttribute( "userSession");
		GridDAO gridDAO = new GridDAO();
		Grid grid = gridDAO.getGridById(id);
		User user = null;
		   
		if( userSession != null){
			user = userSession.getUser();
		}
		
		ReverseAjaxThread thread = ReverseAjaxThread.getInstance();
		thread.addScriptSessionItem(WebContextFactory.get().getHttpServletRequest(), WebContextFactory.get().getScriptSession(), grid);
		   
		Util utilUser = new Util(WebContextFactory.get().getScriptSession());
		
		if( grid != null){
			if( !grid.getOngoing() && !grid.getFinished()){
				Date today = Utils.today();
				String output = "";
				GregorianCalendar todayCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
				GregorianCalendar startDateCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
				todayCalendar.setTime( today);
				startDateCalendar.setTime( grid.getStartDate());
				if( todayCalendar.get( Calendar.YEAR) == startDateCalendar.get( Calendar.YEAR) && todayCalendar.get( Calendar.MONTH) == startDateCalendar.get( Calendar.MONTH) && todayCalendar.get( Calendar.DAY_OF_MONTH) == startDateCalendar.get( Calendar.DAY_OF_MONTH)){
					output = "<p><strong>Starts at<div class='reloj'>" + Utils.getTime(grid.getStartDate())+"</div>";
				} else{
					output = "<p><strong>Starts on " + Utils.getDate(grid.getStartDate(), 4) + " at <div class='reloj'>" + Utils.getTime(grid.getStartDate()) + "</div>";
				}
				utilUser.setValue("status",  output);
			} else{
				utilUser.setValue("freeBoxes", grid.getFreeBoxes());
				utilUser.setValue("boxPrice",  new DecimalFormat( "0").format(grid.getBoxPrice()) );
				   
				if( grid.getIsInPartialWin()){
					Date today = Utils.today();
					long timeLeft = today.getTime() - grid.getPartialWinStartTime().getTime();
					timeLeft = grid.getPartialWinSeconds() - (timeLeft/1000);
					if(timeLeft < 0){
						timeLeft =0;
					}
					int min = (int)timeLeft / 60 ;
					int sec = (int)timeLeft % 60;
					   
					utilUser.setValue("status",  "<p><strong>"+grid.getPartialWinUser().getLogin()+" has found the prize!</strong><br />He will get it if nobody opens a box in:</p><div class='reloj' id='crono'>"+new DecimalFormat( "00").format(min)+":"+new DecimalFormat( "00").format(sec)+"</div>");
					utilUser.setValue("log",  generateLogHtml( grid));
					utilUser.addFunctionCall("stopCronoItem");
					utilUser.addFunctionCall("initCronoItem", timeLeft);
					utilUser.addFunctionCall("showCronoItem");
					
					if( user != null && user.getId().intValue()== grid.getPartialWinUserId().intValue()){
						utilUser.setValue("buyButtonSpan", "<a href='javascript:boxBuyItem("+grid.getId()+",-1);' class='floatRight'><img id='buyButton' class='bb' src='/img/btn_destapar_aleatoria_disabled.png' alt='Open random box' /></a>");
					} else{
						utilUser.setValue("buyButtonSpan", "<a href='javascript:boxBuyItem("+grid.getId()+",-1);' class='floatRight'><img class='bb' id='buyButton' src='/img/btn_destapar_aleatoria.png' alt='Open random box' /></a>");
					}
					
				} else{
					if( grid.getPartialWinPreviousUser() != null){
						utilUser.setValue("status", "<p><strong>Previous winner is "+grid.getPartialWinPreviousUser().getLogin()+"</strong>. <br/>He can still get the prize if nobody reveals the prize box when there are only 2 boxes left.</p>");
					}else{
						utilUser.setValue("status",  "<p>Nobody has found the prize position yet</p>");
					}
					utilUser.setValue("log",  generateLogHtml( grid));
					utilUser.setValue("buyButtonSpan", "<a href='javascript:boxBuyItem("+grid.getId()+",-1);' class='floatRight'><img id='buyButton' class='bb' src='/img/btn_destapar_aleatoria.png' alt='open random box' /></a>");
				}
				
				utilUser.setValue("gridTable", generateItemGridHtml(grid));
				if( grid.getIsInPartialWin() || grid.getFinished()){
					utilUser.setValue("winPosText", grid.getWinPosText());
				}
			}
		}
	}
   
   public synchronized void buyBoxRegularGrid(User user, Grid grid, Box box, String hash) throws Exception
   {
	   while(openingBoxSet.contains(grid.getId())){}
	   
	   openingBoxSet.add( grid.getId());
	   ReverseAjaxThread thread = ReverseAjaxThread.getInstance();
	   GridDAO gridDAO = new GridDAO();
	   GridHistoricDAO gridHistoricDAO = new GridHistoricDAO();
	   UserHistoricDAO userHistoricDAO = new UserHistoricDAO();
	   BoxDAO boxDAO = new BoxDAO();
	   UserDAO userDAO = new UserDAO();
	   Random generator = new Random( new Date().getTime());
	   Date today = Utils.today();
	   Double initialPrice = grid.getBoxPrice();
	   List<Box> freeBoxes = null;
	   
	   String testHash = Utils.digest( "6SjeTkd8n" + user.getEmail() + grid.getId() + grid.getItem().getId() + (box == null ? "null":box.getId()));
	   
	   if( testHash.equals(hash)){
		   //Genera la posición aleatoria
		   int pos = 0;
		   if( box == null){
			   Criteria criteria = new Criteria();
			   criteria.addEqualTo( "type", Box.TYPE_FREE);
			   criteria.addEqualTo( "gridId", grid.getId());
			   freeBoxes = boxDAO.getBoxListByCriteria(criteria, null);
			   pos = freeBoxes.get( generator.nextInt( freeBoxes.size())).getPos();
		   } if( box != null){
			   pos = box.getPos();
		   }
		   //Get ScriptSessions
		   Set<ScriptSession> sessionsAllIndex= thread.getAllScriptSessionsIndex();
		   Set<ScriptSession> scriptSessionUserIndex = thread.getUserScriptSessionsIndex( user.getId().toString());
		   Set<ScriptSession> sessionsExceptUserIndex= thread.getExceptUserScriptSessionsIndex( user.getId().toString());
		   Set<ScriptSession> sessionsAllItem= thread.getAllScriptSessionsItem(grid.getId().toString());
		   Set<ScriptSession> scriptSessionUserItem = thread.getUserScriptSessionsItem( grid.getId().toString(),user.getId().toString());
		   Set<ScriptSession> sessionsExceptUserItem= thread.getExceptUserScriptSessionsItem(grid.getId().toString(),user.getId().toString());
		   
		   Util utilUserIndex = new Util(scriptSessionUserIndex);
		   Util utilAllIndex = new Util(sessionsAllIndex);
		   Util utilAllExceptUserIndex = new Util(sessionsExceptUserIndex);
		   Util utilUserItem = new Util(scriptSessionUserItem);
		   Util utilAllItem = new Util(sessionsAllItem);
		   Util utilAllExceptUserItem = new Util(sessionsExceptUserItem);
		   
		   //Motramos los nuevos créditos al usuario
		   utilUserIndex.setValue("credits", user.getCredits());
		   utilUserItem.setValue("credits", user.getCredits());
		   
		   //Gestion de cuando hay un Partial Win y alguien detapa uan caja
		   if( grid.getIsInPartialWin()){
			   if( grid.getPartialWinUser().getId().intValue() == user.getId().intValue()){
				   openingBoxSet.remove( grid.getId());
				   return;
			   }
			   //Calcula si el tiempo de PartialWin ha terminado
			   long timeLeft = today.getTime() - grid.getPartialWinStartTime().getTime();
			   timeLeft = grid.getPartialWinSeconds() - (timeLeft/1000);
			   if( timeLeft <= 0){
				   openingBoxSet.remove( grid.getId());
				   return;
			   }
			   //Genera la nueva posicion ganadora
			  
			   Criteria criteria = new Criteria();
			   criteria.addEqualTo( "type", Box.TYPE_FREE);
			   criteria.addEqualTo( "gridId", grid.getId());
			   criteria.addNotEqualTo( "pos", pos);
			   List<Box> tempFreeBoxes = boxDAO.getBoxListByCriteria(criteria, null);
			   
			   freeBoxes = new ArrayList<Box>();
			   
			   if( grid.getType() == Grid.GRID_TYPE_MULTIPRIZE){
				   for( Box tempBox:tempFreeBoxes){
					   if( tempBox.getPos().intValue() != grid.getMultiPrize1_1CreditPos() 
						&& tempBox.getPos().intValue() != grid.getMultiPrize1_2CreditPos() 
						&& tempBox.getPos().intValue() != grid.getMultiPrize1_3CreditPos() 
						&& tempBox.getPos().intValue() != grid.getMultiPrize1_4CreditPos() 
						&& tempBox.getPos().intValue() != grid.getMultiPrize1_5CreditPos() 
						&& tempBox.getPos().intValue() != grid.getMultiPrize2_1CreditPos() 
						&& tempBox.getPos().intValue() != grid.getMultiPrize2_1CreditPos() 
						&& tempBox.getPos().intValue() != grid.getMultiPrize5CreditPos() 
						&& tempBox.getPos().intValue() != grid.getMultiPrize10CreditPos()){
						   freeBoxes.add( tempBox);
					   }
				   }
				   
				   if(freeBoxes.size() <=2){
					   freeBoxes = new ArrayList<Box>();
					   freeBoxes.addAll( tempFreeBoxes);
				   }
			   } else{
				   freeBoxes.addAll( tempFreeBoxes);
			   }
				   
				   
			   
			   utilAllItem.removeClassName("b"+grid.getWinPos(), "cajaPremiada");
			   utilAllItem.addClassName("b"+grid.getWinPos(), "cajaAbierta");
			   
			   
			   
			   grid.setWinPos( freeBoxes.get( generator.nextInt( freeBoxes.size() )).getPos());
			   
			   //genera el nuevo winPosHash
			   int randomValue = generator.nextInt(5);
				String winPosText = "";
				switch (randomValue){
					case 0: winPosText = "Box=" + grid.getWinPos() + " randomCode=" + generateCode(10);break;
					case 1: winPosText = "prize position= " + grid.getWinPos() + " ignoreThis=" + generateCode(6);break;
					case 2: winPosText = "PoSiTiOn: " + grid.getWinPos() + " CoDe:" + generateCode(8);break;
					case 3: winPosText = "Box With Prize= " + grid.getWinPos() + " Code=" + generateCode(12);break;
					case 4: winPosText = "THE PRIZE IS IN BOX " + grid.getWinPos() + ", RANDOM CODE " + generateCode(5);break;
				}
				
				grid.setWinPosText(winPosText);
				grid.setWinPosHash(Utils.digestMD5(winPosText));
				utilAllItem.setValue("winPosHash", grid.getWinPosHash());
				utilAllItem.setValue("winPosText", "");
			   
		   }
		   
		   
		   //Generar historico
		   GridHistoric gridHistoric = new GridHistoric();
		   gridHistoric.setCreated( today);
		   gridHistoric.setGrid(grid);
		   gridHistoric.setGridId(grid.getId());
		   gridHistoric.setType( GridHistoric.BUY_BOX);
		   gridHistoric.setValue1( new Double(pos));
		   gridHistoric.setValue2( user.getLogin());
		   gridHistoricDAO.setGridHistoric(gridHistoric);
		   
		   UserHistoric userHistoric = new UserHistoric();
		   userHistoric.setCreated( today);
		   userHistoric.setUser(user);
		   userHistoric.setUserId(user.getId());
		   userHistoric.setType( UserHistoric.BUY_BOX);
		   userHistoric.setValue1( new Double(initialPrice));
		   userHistoric.setValue2( Integer.toString(pos));
		   userHistoric.setValue3( grid.getItem().getName());
		   userHistoricDAO.setUserHistoric(userHistoric);
		   
		   //Calcular precios y cajas
		   boolean priceHasChanged = false;
		   grid.setFreeBoxes( grid.getFreeBoxes() - 1);
		   grid.setMoneyWon( grid.getMoneyWon() + grid.getBoxPrice());
		   
		   if( grid.getType().intValue() != Grid.GRID_TYPE_FIXED_PRICE && grid.getType().intValue() != Grid.GRID_TYPE_FREE){
			   double boxPrice = 1; 
				
				if( grid.getItem().getType() == Item.ITEM_TYPE_ONLY_BITCOINS){
					boxPrice = Math.round( (grid.getItem().getBitcoins() * 1000) / grid.getFreeBoxes());
				} else if( grid.getItem().getType() == Item.ITEM_TYPE_ONLY_CREDITS){
					boxPrice = Math.round( grid.getItem().getCredits()  / grid.getFreeBoxes());
				} else if( grid.getItem().getType() == Item.ITEM_TYPE_CREDITS_AND_BITCOINS){
					boxPrice = Math.round( ((grid.getItem().getBitcoins() * 1000) + grid.getItem().getCredits())  / grid.getFreeBoxes());
				}
				
			   if( boxPrice < 1){ 
				   boxPrice = 1;
			   }
		   
			   if( grid.getBoxPrice().doubleValue() != new Double(Math.round(boxPrice)).doubleValue()){
				   priceHasChanged = true;
				   grid.setBoxPrice(new Double(Math.round(boxPrice)));
				   gridDAO.setGrid(grid);
			   }
		   }
		   
		   grid.setBoughtBoxes( grid.getBoughtBoxes() + 1);
		   
		   //Actualizar objeto box
		   if( box == null){
			   Criteria criteria = new Criteria();
			   criteria.addEqualTo( "gridId", grid.getId());
			   criteria.addEqualTo( "pos", pos);
			   box = boxDAO.getBoxByCriteria(criteria);
		   }
		   box.setUser(user);
		   box.setUserId(user.getId());
		   box.setType(Box.TYPE_BOUGHT);
		   box.setPrice( initialPrice);
		   
		   boxDAO.setBox(box);
		   
		   //Gestionar acierto
		   if( pos == grid.getWinPos()){
			   //Gestiona cuando quedaban solo 2 cajas y un usuario destapa 1
			   if( grid.getType().intValue() == Grid.GRID_TYPE_WINNER_IS_FIRST){
				   utilAllItem.setValue("b"+box.getPos(), "<strong>" + box.getPos() + "</strong>");
				   utilAllItem.addClassName("b"+box.getPos(), "cajaPremiada bb");
				   
				   if( grid.getItem().getType().intValue() == Item.ITEM_TYPE_ONLY_BITCOINS){
					   if( user.getAutoPay()){
						   utilUserIndex.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "We will send the " + grid.getItem().getName()+" within the next 24 hours." );
						   utilUserItem.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "We will send the " + grid.getItem().getName()+" within the next 24 hours." );
					   } else{
						   utilUserIndex.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "Claim your prize clicking in the Prizes button above." );
						   utilUserItem.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "Claim your prize clicking in the Prizes button above." );
					   }
				   }
				   if( grid.getItem().getType().intValue() == Item.ITEM_TYPE_CREDITS_AND_BITCOINS){
					   if( user.getAutoPay()){
						   utilUserIndex.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "We will send the " + grid.getItem().getName()+" within the next 24 hours. The " + grid.getItem().getCredits() +" credits have already been added to your account." );
						   utilUserItem.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "We will send the " + grid.getItem().getName()+" within the next 24 hours. The " + grid.getItem().getCredits() +" credits have already been added to your account." );
					   } else{
						   utilUserIndex.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "Claim your Bitcoin prize clicking in the Prizes button above. The credits have already been added to your account." );
						   utilUserItem.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "Claim your Bitcoin prize clicking in the Prizes button above. The credits have already been added to your account." );
					   }
				   }
				   if( grid.getItem().getType().intValue() == Item.ITEM_TYPE_ONLY_CREDITS){
					   utilUserIndex.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "The credits have already been added to your account." );
					   utilUserItem.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "The credits have already been added to your account." );
				   }
				   
				   manageWin(grid, user, false);
				  
			   } else if( grid.getFreeBoxes()==1){
				   utilAllItem.setValue("b"+box.getPos(), "<strong>" + box.getPos() + "</strong>");
				   utilAllItem.addClassName("b"+box.getPos(), "cajaPremiada bb");
				   
				   if( grid.getItem().getType().intValue() == Item.ITEM_TYPE_ONLY_BITCOINS){
					   if( user.getAutoPay()){
						   utilUserIndex.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "We will send the " + grid.getItem().getName()+" within the next 24 hours." );
						   utilUserItem.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "We will send the " + grid.getItem().getName()+" within the next 24 hours." );
					   } else{
						   utilUserIndex.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "Claim your prize clicking in the Prizes button above." );
						   utilUserItem.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "Claim your prize clicking in the Prizes button above." );
					   }
				   }
				   if( grid.getItem().getType().intValue() == Item.ITEM_TYPE_CREDITS_AND_BITCOINS){
					   if( user.getAutoPay()){
						   utilUserIndex.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "We will send the " + grid.getItem().getName()+" within the next 24 hours. The " + grid.getItem().getCredits() +" credits have already been added to your account." );
						   utilUserItem.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "We will send the " + grid.getItem().getName()+" within the next 24 hours. The " + grid.getItem().getCredits() +" credits have already been added to your account." );
					   } else{
						   utilUserIndex.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "Claim your Bitcoin prize clicking in the Prizes button above. The credits have already been added to your account." );
						   utilUserItem.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "Claim your Bitcoin prize clicking in the Prizes button above. The credits have already been added to your account." );
					   }
				   }
				   if( grid.getItem().getType().intValue() == Item.ITEM_TYPE_ONLY_CREDITS){
					   utilUserIndex.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "The credits have already been added to your account." );
					   utilUserItem.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "The credits have already been added to your account." );
				   }
				   
				   manageWin(grid, user, false);
			   } else{
				   GridHistoric gridHistoricPartialWin = new GridHistoric();
				   gridHistoricPartialWin.setCreated( today);
				   gridHistoricPartialWin.setGrid(grid);
				   gridHistoricPartialWin.setGridId(grid.getId());
				   gridHistoricPartialWin.setType( GridHistoric.PARTIAL_WIN);
				   gridHistoricPartialWin.setValue1( new Double (pos));
				   gridHistoricPartialWin.setValue2( user.getLogin());
				   gridHistoricDAO.setGridHistoric(gridHistoricPartialWin);
				   
				   UserHistoric userHistoricPartialWin = new UserHistoric();
				   userHistoricPartialWin.setCreated( today);
				   userHistoricPartialWin.setUser(user);
				   userHistoricPartialWin.setUserId(user.getId());
				   userHistoricPartialWin.setType( UserHistoric.PARTIAL_WIN);
				   userHistoricPartialWin.setValue2( Integer.toString(pos));
				   userHistoricPartialWin.setValue3( grid.getItem().getName());
				   userHistoricDAO.setUserHistoric(userHistoricPartialWin);
				   
				   grid.setIsInPartialWin( true);
				   grid.setPartialWinUser(user);
				   
				   grid.setPartialWinUserId( user.getId());
				   grid.setPartialWinStartTime(Utils.today());
				   grid.setPartialWinPreviousBoxId(pos);
				   
				   long timeLeft = today.getTime() - grid.getPartialWinStartTime().getTime();
				   timeLeft = grid.getPartialWinSeconds() - (timeLeft/1000);
				   int min = (int)timeLeft / 60 ;
				   int sec = (int)timeLeft % 60;
				   
				   if(timeLeft < 0){
					   timeLeft =0;
				   }
				   
				   if( min == 0){
					   utilUserIndex.addFunctionCall("playAnimation", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "But wait! You still have to wait " + sec + " " + (sec == 1 ? "second" : "seconds") + ". Any other user could still open a new box and reset the game.");
					   utilUserItem.addFunctionCall("playAnimation", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "But wait! You still have to wait " + sec + " " + (sec == 1 ? "second" : "seconds") + " seconds. Any other user could still open a new box and reset the game.");
				   } else{
					   if( sec == 0){
						   utilUserIndex.addFunctionCall("playAnimation", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "But wait! You still have to wait " + min +" " + (min == 1 ? "minute" : "minutes") +". Any other user could still open a new box and reset the game.");
						   utilUserItem.addFunctionCall("playAnimation", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "But wait! You still have to wait " + min +" " + (min == 1 ? "minute" : "minutes") +". Any other user could still open a new box and reset the game.");
					   } else{
						   utilUserIndex.addFunctionCall("playAnimation", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "But wait! You still have to wait " + min +" " + (min == 1 ? "minute" : "minutes") +" and " + sec + " " + (sec == 1 ? "second" : "seconds") + ". Any other user could still open a new box and reset the game.");
						   utilUserItem.addFunctionCall("playAnimation", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "But wait! You still have to wait " + min +" " + (min == 1 ? "minute" : "minutes") +" and " + sec + " " + (sec == 1 ? "second" : "seconds") + ". Any other user could still open a new box and reset the game.");
					   }
				   }
				   
				   utilAllIndex.setValue("log_" + grid.getId(), "<p><strong>" + grid.getPartialWinUser().getLogin() +" has found the prize!</strong><br />He will get it if nobody opens a box in:</p><div class='reloj' id='crono"+grid.getId()+"'>"+new DecimalFormat( "00").format(min)+":"+new DecimalFormat( "00").format(sec)+"</div>");
				   utilAllIndex.addFunctionCall("stopCrono", grid.getId());
				   utilAllIndex.addFunctionCall("initCrono", grid.getId(), grid.getPartialWinSeconds());
				   utilAllIndex.addFunctionCall("showCrono", grid.getId());
				   
				   
				   utilAllExceptUserIndex.setValue("buyButtonSpan_" + grid.getId(), "<a href='javascript:boxBuy("+grid.getId()+"," + new DecimalFormat( "0").format(grid.getBoxPrice()) + ",-1);' ><img src='/img/btn_destapar.png' alt='Open box' class='bb destaparCaja' id='buyButton"+grid.getId()+"'/></a>");
				   utilUserIndex.setValue("buyButtonSpan_" + grid.getId(), "<a href='javascript:boxBuy("+grid.getId()+"," + new DecimalFormat( "0").format(grid.getBoxPrice()) + ",-1);' ><img src='/img/btn_destapar_disabled.png' alt='Open box' class='bb destaparCaja' id='buyButton"+grid.getId()+"'/></a>");
				   utilUserIndex.addFunctionCall( "calibrateClock",Utils.today().getTime());
	
				   utilAllItem.setValue("status",  "<p><strong>"+grid.getPartialWinUser().getLogin()+" has found the prize!</strong><br />He will get it if nobody opens a box in:</p><div class='reloj' id='crono'>"+new DecimalFormat( "00").format(min)+":"+new DecimalFormat( "00").format(sec)+"</div>");
				   utilAllItem.setValue("log",  generateLogHtml(grid));
				   utilAllItem.addFunctionCall("stopCronoItem");
				   utilAllItem.addFunctionCall("initCronoItem", grid.getPartialWinSeconds());
				   utilAllItem.addFunctionCall("showCronoItem");
				   
				   utilAllExceptUserItem.setValue("buyButtonSpan", "<a href='javascript:boxBuyItem("+grid.getId()+",-1);' class='floatRight'><img id='buyButton' class='bb' src='/img/btn_destapar_aleatoria.png' alt='Open random box' /></a>");
				   utilUserItem.setValue("buyButtonSpan", "<a href='javascript:boxBuyItem("+grid.getId()+",-1);' class='floatRight'><img id='buyButton' class='bb' src='/img/btn_destapar_aleatoria_disabled.png' alt='Open random box' /></a>");
				   utilUserItem.addFunctionCall( "calibrateClock",Utils.today().getTime());
				   
				   utilAllItem.setValue("b"+box.getPos(), "<strong>" + box.getPos() + "</strong>");
				   utilAllItem.addClassName("b"+box.getPos(), "cajaPremiada bb");
			   }
			   utilAllItem.setValue("winPosText", grid.getWinPosText());
				   
		   } else{
			   int creditsAdded = 0;
			   if( grid.getIsInPartialWin()){
				   grid.setPartialWinPreviousUser( grid.getPartialWinUser());
				   grid.setPartialWinPreviousUserId( grid.getPartialWinUser().getId());
			   }
			   grid.setIsInPartialWin( false);
			   grid.setPartialWinUser( null);
			   grid.setPartialWinUserId( null);
			   grid.setPartialWinStartTime( null);
			   
			   //Si no se acierta y sólo quedaba una caja o, si es juego de precio fijo, si se gasta menos en abrir las cajas que quedan que el valor del premio
			   
			   int prizeValue = 0;
			   
			   if( grid.getItem().getType() == Item.ITEM_TYPE_ONLY_BITCOINS){
				   prizeValue = new Double(grid.getItem().getBitcoins() * 1000d).intValue() ;
			   } else if( grid.getItem().getType() == Item.ITEM_TYPE_ONLY_CREDITS){
        		  prizeValue = grid.getItem().getCredits();
			   } else if( grid.getItem().getType() == Item.ITEM_TYPE_CREDITS_AND_BITCOINS){
        		  prizeValue = grid.getItem().getCredits();
        		  prizeValue = prizeValue + new Double(grid.getItem().getBitcoins() * 1000d).intValue();
			   }
			   
			   if( grid.getType() == Grid.GRID_TYPE_MULTIPRIZE){
				   
				   if( pos == grid.getMultiPrize1_1CreditPos() ||  pos == grid.getMultiPrize1_2CreditPos()  || pos == grid.getMultiPrize1_3CreditPos() ||  pos == grid.getMultiPrize1_4CreditPos() ||  pos == grid.getMultiPrize1_5CreditPos()){
					   user.setCredits( user.getCredits() + 1);
					   userDAO.setUser(user);
					   creditsAdded = 1;
				   } else if( pos == grid.getMultiPrize2_1CreditPos() ||  pos == grid.getMultiPrize2_2CreditPos()  ){
					   user.setCredits( user.getCredits() + 2);
					   userDAO.setUser(user);
					   creditsAdded = 2;
				   } else if( pos == grid.getMultiPrize5CreditPos() ){
					   user.setCredits( user.getCredits() + 5);
					   userDAO.setUser(user);
					   creditsAdded = 5;
				   } else if( pos == grid.getMultiPrize10CreditPos() ){
					   user.setCredits( user.getCredits() + 10);
					   userDAO.setUser(user);
					   creditsAdded = 10;
				   }
				   
				   if( creditsAdded != 0){
					   UserHistoric userHistoricMultiprize = new UserHistoric();
					   userHistoricMultiprize.setCreated( today);
					   userHistoricMultiprize.setUser(user);
					   userHistoricMultiprize.setUserId(user.getId());
					   userHistoricMultiprize.setType( UserHistoric.GET_CREDIT_IN_MULTIPRIZE);
					   userHistoricMultiprize.setValue1( new Double(creditsAdded));
					   userHistoricMultiprize.setValue2( Integer.toString(pos));
					   userHistoricMultiprize.setValue3( grid.getItem().getName());
					   userHistoricDAO.setUserHistoric(userHistoricMultiprize);
					   
					   GridHistoric gridHistoricMultiprize = new GridHistoric();
					   gridHistoricMultiprize.setCreated( today);
					   gridHistoricMultiprize.setGrid(grid);
					   gridHistoricMultiprize.setGridId(grid.getId());
					   gridHistoricMultiprize.setType( GridHistoric.FIND_MULTIPRIZE);
					   gridHistoricMultiprize.setValue1( new Double (creditsAdded));
					   gridHistoricMultiprize.setValue2( Integer.toString(pos));
					   gridHistoricMultiprize.setValue3( user.getLogin());
					   gridHistoricDAO.setGridHistoric(gridHistoricMultiprize);
					   
					   utilUserIndex.setValue("credits", user.getCredits());
					   utilUserItem.setValue("credits", user.getCredits());
					   utilUserIndex.addFunctionCall("playAnimationC", creditsAdded, "/img/item/credits"+creditsAdded+".png" );
					   utilUserItem.addFunctionCall("playAnimationC", creditsAdded, "/img/item/credits"+creditsAdded+".png" );
				   }
				   
			   }
			   
			   
			   if( grid.getFreeBoxes()==1 || ( grid.getType() == Grid.GRID_TYPE_FIXED_PRICE && (grid.getFreeBoxes() * grid.getBoxPrice()<=prizeValue))){
				   grid.setFinished( true);
				   grid.setOngoing( false);
				   grid.setPartialWinSeconds( 0);
				   grid.setFinishDate(today);
				   gridDAO.setGrid(grid);
				   
				   if( grid.getPartialWinPreviousUser() != null){
					   //Pone la ultima caja acertada como ganadora
					   Criteria criteria = new Criteria();
					   criteria.addEqualTo("pos", grid.getPartialWinPreviousBoxId());
					   criteria.addEqualTo("gridId", grid.getId());
					   Box winBox = boxDAO.getBoxByCriteria(criteria);
					   winBox.setType( Box.TYPE_WIN);
					   boxDAO.setBox(winBox);
					   grid.setWinPos( winBox.getPos());
					   gridDAO.setGrid(grid);
					   
					   
					   
					   Set<ScriptSession> scriptSessionPreviousUserIndex = thread.getUserScriptSessionsIndex( grid.getPartialWinPreviousUser().getId().toString());
					   Set<ScriptSession> scriptSessionPreviousUserItem = thread.getUserScriptSessionsItem( grid.getPartialWinPreviousUser().getId().toString(),user.getId().toString());
					   if( scriptSessionPreviousUserIndex != null){
						   Util utilPreviousUserIndex = new Util(scriptSessionPreviousUserIndex);
						   if( grid.getItem().getType().intValue() == Item.ITEM_TYPE_ONLY_BITCOINS){
							   if( grid.getPartialWinPreviousUser().getAutoPay()){
								   utilPreviousUserIndex.addFunctionCall("playAnimationB", grid.getPartialWinPreviousUser().getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "We will send the " + grid.getItem().getName()+" within the next 24 hours." );
							   } else{
								   utilPreviousUserIndex.addFunctionCall("playAnimationB", grid.getPartialWinPreviousUser().getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "Claim your prize clicking in the Prizes button above." );
							   }
						   }
						   if( grid.getItem().getType().intValue() == Item.ITEM_TYPE_CREDITS_AND_BITCOINS){
							   if( grid.getPartialWinPreviousUser().getAutoPay()){
								   utilPreviousUserIndex.addFunctionCall("playAnimationB", grid.getPartialWinPreviousUser().getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "We will send the " + grid.getItem().getName()+" within the next 24 hours. The " + grid.getItem().getCredits() +" credits have already been added to your account." );
							   } else{
								   utilPreviousUserIndex.addFunctionCall("playAnimationB", grid.getPartialWinPreviousUser().getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "Claim your Bitcoin prize clicking in the Prizes button above. The credits have already been added to your account." );
							   }
						   }
						   if( grid.getItem().getType().intValue() == Item.ITEM_TYPE_ONLY_CREDITS){
							   utilPreviousUserIndex.addFunctionCall("playAnimationB", grid.getPartialWinPreviousUser().getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "The credits have already been added to your account." );
						   }
					   }
					   if( scriptSessionPreviousUserItem != null){
						   Util utilPreviousUserItem = new Util(scriptSessionPreviousUserItem);
						   if( grid.getItem().getType().intValue() == Item.ITEM_TYPE_ONLY_BITCOINS){
							   if( grid.getPartialWinPreviousUser().getAutoPay()){
								   utilPreviousUserItem.addFunctionCall("playAnimationB", grid.getPartialWinPreviousUser().getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "We will send the " + grid.getItem().getName()+" within the next 24 hours." );
							   } else{
								   utilPreviousUserItem.addFunctionCall("playAnimationB", grid.getPartialWinPreviousUser().getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "Claim your prize clicking in the Prizes button above." );
							   }
						   }
						   if( grid.getItem().getType().intValue() == Item.ITEM_TYPE_CREDITS_AND_BITCOINS){
							   if( grid.getPartialWinPreviousUser().getAutoPay()){
								   utilPreviousUserItem.addFunctionCall("playAnimationB", grid.getPartialWinPreviousUser().getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "We will send the " + grid.getItem().getName()+" within the next 24 hours. The " + grid.getItem().getCredits() +" credits have already been added to your account." );
							   } else{
								   utilPreviousUserItem.addFunctionCall("playAnimationB", grid.getPartialWinPreviousUser().getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "Claim your Bitcoin prize clicking in the Prizes button above. The credits have already been added to your account." );
							   }
						   }
						   if( grid.getItem().getType().intValue() == Item.ITEM_TYPE_ONLY_CREDITS){
							   utilPreviousUserItem.addFunctionCall("playAnimationB", grid.getPartialWinPreviousUser().getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "The credits have already been added to your account." );
						   }
					   }
					   
					   manageWin(grid, grid.getPartialWinPreviousUser(), true);
					   
				   } else{
					   //Crea el log de no win
					   GridHistoric gridHistoricWin = new GridHistoric();
					   gridHistoricWin.setCreated( Utils.today());
					   gridHistoricWin.setGrid(grid);
					   gridHistoricWin.setGridId(grid.getId());
					   gridHistoricWin.setType( GridHistoric.NO_WIN);
					   gridHistoricDAO.setGridHistoric(gridHistoricWin);
					   
					   // Se devuelven los créditos a todos.
					   
					   if(  grid.getType() != Grid.GRID_TYPE_FREE && grid.getType() != Grid.GRID_TYPE_DOUBLE_OR_NOTHING){
						   Criteria criteria = new Criteria();
						   criteria.addEqualTo("gridId", grid.getId());
						   List<Box> boxList = boxDAO.getBoxListByCriteria(criteria, null);
						   HashMap<Integer,Double> userCredits = new HashMap<Integer, Double>();
						   for( Box box2: boxList){
							   if( box2.getType().intValue() == Box.TYPE_BOUGHT && box2.getUser() != null){
								   Double credits =  userCredits.get( box2.getUserId());
								   if( credits == null){
									   credits = new Double(0);
								   } 
								   credits = credits + box2.getPrice();
								   userCredits.put( box2.getUserId(), credits);
							   }
						   }
						   
						   for( Integer userId :userCredits.keySet()){
							   User userToReturnCredits = userDAO.getUserById( userId);
							   userToReturnCredits.setCredits(userToReturnCredits.getCredits() + userCredits.get( userId).intValue());
							   userDAO.setUser( userToReturnCredits);
							   
							   
							 //Crea el log de no win en userHistoric
							   UserHistoric userHistoricNoWin = new UserHistoric();
							   userHistoricNoWin.setCreated( Utils.today());
							   userHistoricNoWin.setUser(userToReturnCredits);
							   userHistoricNoWin.setUserId(userToReturnCredits.getId());
							   userHistoricNoWin.setType(UserHistoric.GET_CREDIT_BY_REFUND);
							   userHistoricNoWin.setValue1(userCredits.get( userId));
							   userHistoricNoWin.setValue2(grid.getItem().getName());
	
							   userHistoricDAO.setUserHistoric(userHistoricNoWin);
							   
							   
							   ReturnCreditsMailObject returnCreditsMailObject = new ReturnCreditsMailObject( userToReturnCredits.getLogin(), userCredits.get( userId).intValue(), grid.getItem().getName(), Utils.getBaseUrl());
							   Mail mail = new Mail( userToReturnCredits.getEmail(), "You've got a credit refund on Instantri.ch", returnCreditsMailObject);
							   mail.start();
							   
							   mail.join(5000);
							   
							   Set<ScriptSession> scriptSessionUserToReturnCredits = thread.getUserScriptSessionsIndex( userId.toString());
							   if( scriptSessionUserToReturnCredits != null){
								   Util utilUserToReturnCredits = new Util( scriptSessionUserToReturnCredits);
								   utilUserToReturnCredits.setValue("credits" , userToReturnCredits.getCredits());
								   utilUserToReturnCredits.addFunctionCall("trHighlightItem", "credits");
							   }
						   }
					   }
				   }
				   utilAllItem.setValue("winPosText", grid.getWinPosText());
			   }
			   
			   utilAllIndex.setValue("log_" + grid.getId(), generateLogHtml(grid));
			   utilAllIndex.setValue("buyButtonSpan_" + grid.getId(), "<a href='javascript:boxBuy("+grid.getId()+"," + new DecimalFormat( "0").format(grid.getBoxPrice()) + ",-1);' ><img src='/img/btn_destapar.png' alt='Open box' class='bb destaparCaja' id='buyButton"+grid.getId()+"'/></a>");
			   utilAllIndex.addFunctionCall("stopCrono", grid.getId());
			   
			   utilAllItem.setValue("log" , generateLogHtml(grid));
			   if( grid.getPartialWinPreviousUser() != null){
				   utilAllItem.setValue("status", "<p><strong>Previous winner is "+grid.getPartialWinPreviousUser().getLogin()+"</strong> who still can get the prize if nobody found the prize box when there are only 2 boxes left.</p>");
			   }else{
				   utilAllItem.setValue("status",  "<p>Nobody has found the prize yet</p>");
			   }
			   utilAllItem.setValue("buyButtonSpan" , "<a href='javascript:boxBuyItem("+grid.getId()+",-1);' class='floatRight'><img id='buyButton' class='bb' src='/img/btn_destapar_aleatoria.png' alt='Open random box' /></a>");
			   utilAllItem.addFunctionCall("stopCronoItem");
			   
			   utilAllItem.setValue("b"+box.getPos(), "<strong>" + box.getPos() + "</strong>");
			   if( grid.getType() == Grid.GRID_TYPE_MULTIPRIZE && creditsAdded != 0){
				   utilAllItem.addClassName("b"+box.getPos(), "cajaPremiada"+creditsAdded+" bb");
			   } else {
				   utilAllItem.addClassName("b"+box.getPos(), "cajaAbierta bb");
			   }
			   
			  
		   }
		   
		   gridDAO.setGrid( grid);
	
		   //Enviar html por ajax
		   utilAllIndex.setValue("freeBoxes_" + grid.getId(), "<strong>"+grid.getFreeBoxes()+"</strong> Unopened boxes");
		   utilAllIndex.addFunctionCall("trHighlight", "freeBoxes_" + grid.getId());
		   
		   utilAllItem.setValue("freeBoxes", grid.getFreeBoxes());
		   utilAllItem.addFunctionCall("trHighlightItem", "freeBoxes");
		   
		   
		   DecimalFormat numberFormat = new DecimalFormat("#.##");
		   float probability = ((1f/(float)grid.getFreeBoxes()) * 100f);
		   
		   utilAllItem.setValue("probability", numberFormat.format(probability) + "%");
		   
		   if( priceHasChanged){
			   utilAllIndex.setValue("boxPrice_" + grid.getId(), new DecimalFormat( "0").format(grid.getBoxPrice()));
			   utilAllIndex.addFunctionCall("trHighlight", "boxPrice_" + grid.getId());
			   
			   utilAllItem.setValue("boxPrice", new DecimalFormat( "0").format(grid.getBoxPrice()) );
			   utilAllItem.addFunctionCall("trHighlightItem", "boxPrice");
		   }
		   utilAllIndex.addFunctionCall("hideAllBubblePopups");
		   utilAllItem.addFunctionCall("hideAllBubblePopupsDelayed");
		   utilUserIndex.addFunctionCall("setBuyingToFalse");
		   utilUserItem.addFunctionCall("setBuyingToFalse");
	   }
	   
	   openingBoxSet.remove( grid.getId());
   }
   
   
   public void showGridsJob() throws Exception
   {
	   String htmlAllExceptUser = "";
	   String htmlOnlyUser = "";
	   String htmlAll = "";
	   String shortInfo = "";
	   List<Grid> gridList = getGridList();
	   Grid grid = null;
	   ReverseAjaxThread thread = ReverseAjaxThread.getInstance();
	   
	   ParameterDAO parameterDAO = new ParameterDAO();
	   User user = null;
	   Date today = Utils.today();
	    
	   for( int i=0;i<12;i++){
		   htmlAllExceptUser = "";
		   htmlOnlyUser = "";
		   htmlAll = "";
		   shortInfo = "";
			   
		   if( i<gridList.size()){
			   grid = gridList.get( i);
		   } else {
			   grid = null;
		   }
		   
		   //Comprueba si un sorteo empieza
		   if( grid != null){
			   if (grid.getFinished() == false && grid.getOngoing() == false && today.after( grid.getStartDate())){
				   grid.setOngoing( true);
				   GridDAO gridDAO = new GridDAO();
				   gridDAO.setGrid(grid);
				   if( grid.getBoughtBoxes() == 0){
					   GridHistoric gridHistoricStart = new GridHistoric();
					   GridHistoricDAO gridHistoricDAO = new GridHistoricDAO();
					   gridHistoricStart.setCreated( Utils.today());
					   gridHistoricStart.setGrid(grid);
					   gridHistoricStart.setGridId(grid.getId());
					   gridHistoricStart.setType( GridHistoric.START);
					   gridHistoricDAO.setGridHistoric(gridHistoricStart);
				   }
			   }
		   }
				
		   Criteria criteria = new Criteria();
		   criteria.addLike("name", "dwrTr" + i);
		   Parameter parameter = parameterDAO.getParameterByCriteria(criteria);
		   if( parameter == null){
			   parameter = new Parameter();
			   parameter.setName( "dwrTr" + i);
			   parameter.setValue( "");
		   }
		   if( grid != null){
			   shortInfo = grid.getId().toString() + grid.getOngoing();
		   }
		   
		   String hash = Utils.digest( shortInfo);
		    	
		   if( !hash.equals( parameter.getValue())){
			   if( grid != null){
				   if( grid.getIsInPartialWin()){
					   user = grid.getPartialWinUser();
					   Set<ScriptSession> sessionsAllIndex= thread.getAllScriptSessionsIndex();
					   Set<ScriptSession> scriptSessionUserIndex = thread.getUserScriptSessionsIndex( grid.getPartialWinUser().getId().toString());
					   Set<ScriptSession> sessionsExceptUserIndex= thread.getExceptUserScriptSessionsIndex( grid.getPartialWinUser().getId().toString());
					   
					   Set<ScriptSession> sessionsAllItem= thread.getAllScriptSessionsItem(grid.getId().toString());
					   Set<ScriptSession> scriptSessionUserItem = thread.getUserScriptSessionsItem( grid.getId().toString(), grid.getPartialWinUser().getId().toString());
					   Set<ScriptSession> sessionsExceptUserItem= thread.getExceptUserScriptSessionsItem( grid.getId().toString(), grid.getPartialWinUser().getId().toString());
					   
					   Util utilAllIndex = new Util(sessionsAllIndex);
					   Util utilUserIndex = new Util( scriptSessionUserIndex);
					   Util utilAllExceptUserIndex = new Util(sessionsExceptUserIndex);
					   
					   Util utilAllItem = new Util( sessionsAllItem);
					   Util utilUserItem = new Util( scriptSessionUserItem);
					   Util utilAllExceptUserItem = new Util( sessionsExceptUserItem);
					   
					   htmlAllExceptUser = generateGridHtml(grid, null, true);
					   htmlOnlyUser = generateGridHtml(grid,user, true);
					   
					   utilAllExceptUserIndex.setValue("mtr" + i, htmlAllExceptUser);
					   utilUserIndex.setValue("mtr" + i, htmlOnlyUser);
					   
					   //Codigo de Item
						long timeLeft = today.getTime() - grid.getPartialWinStartTime().getTime();
						timeLeft = grid.getPartialWinSeconds() - (timeLeft/1000);
						if(timeLeft < 0){
							timeLeft =0;
						}
						int min = (int)timeLeft / 60 ;
						int sec = (int)timeLeft % 60;
						   
						utilAllItem.setValue("status",  "<p><strong>"+grid.getPartialWinUser().getLogin()+" has found the prize!</strong><br />He will get it if nobody opens a box in:</p><div class='reloj' id='crono'>"+new DecimalFormat( "00").format(min)+":"+new DecimalFormat( "00").format(sec)+"</div>");
						utilAllItem.setValue("log",  generateLogHtml(grid));
						utilUserItem.setValue("buyButtonSpan", "<input type='button' disabled='true' value='Open random box' id='buyButton' class='bb' onclick='javascript:boxBuyItem("+grid.getId()+",-1);'/>");
						utilAllExceptUserItem.setValue("buyButtonSpan", "<input type='button' value='Open random box' id='buyButton' class='bb' onclick='javascript:boxBuyItem("+grid.getId()+",-1);'/>");
						utilAllItem.addFunctionCall("stopCronoItem");
						utilAllItem.addFunctionCall("initCronoItem", timeLeft);
						utilAllItem.addFunctionCall("showCronoItem");  
						utilAllItem.setValue("winPosHash",  grid.getWinPosHash());
						utilAllItem.setValue("winPosText",  grid.getWinPosText());
					   //Fin codigo item
					   
					   utilAllIndex.addFunctionCall("stopCrono",  grid.getId());
					   utilAllIndex.addFunctionCall("initCrono",  grid.getId(), (int)timeLeft);
					   utilAllIndex.addFunctionCall("showCrono",  grid.getId()); 
					   utilAllIndex.addFunctionCall("reloadCluetip");
				   } else{
					   Set<ScriptSession> sessionsAllIndex= thread.getAllScriptSessionsIndex();
					   Set<ScriptSession> sessionsAllItem= thread.getAllScriptSessionsItem( grid.getId().toString());
					   Util utilAllIndex = new Util(sessionsAllIndex);
					   Util utilAllItem = new Util(sessionsAllItem);
					   
					   //codigo html de item
					   utilAllItem.setValue("freeBoxes", grid.getFreeBoxes());
					   utilAllItem.setValue("boxPrice",  new DecimalFormat( "0").format(grid.getBoxPrice()) );
					   if( grid.getPartialWinPreviousUser() != null){
						   utilAllItem.setValue("status", "<p><strong>Previous winner is "+grid.getPartialWinPreviousUser().getLogin()+"</strong>. <br/>You still can win the prize if nobody finds the prize box when there are only 2 boxes left.</p>");
					   }else{
						   utilAllItem.setValue("status",  "<p>Nobody has found the prize yet.</p>");
					   }
					   utilAllItem.setValue("log",  generateLogHtml( grid));
					   utilAllItem.setValue("buyButtonSpan", "<a href='javascript:boxBuyItem("+grid.getId()+",-1);' class='floatRight'><img id='buyButton' class='bb' src='/img/btn_destapar_aleatoria.png' alt='Open random box' /></a>");
					   utilAllItem.setValue("gridTable", generateItemGridHtml(grid));
					   utilAllItem.setValue("winPosHash",  grid.getWinPosHash());
					   utilAllItem.setValue("winPosText",  "");
					   //Fin codigo de item
					   
					   htmlAll = generateGridHtml(grid,  null, true);
					   utilAllIndex.setValue("mtr" + i,  htmlAll);
					   utilAllIndex.addFunctionCall("reloadCluetip");
				   }
			   } else{
				   Set<ScriptSession> sessionsAll= thread.getAllScriptSessionsIndex();
				   Util utilAll = new Util(sessionsAll);
				   
				   utilAll.setValue("mtr" + i, "");
				   utilAll.addFunctionCall("reloadCluetip");
			   }
			   parameter.setValue( hash);
			   parameterDAO.setParameter(parameter);
		   }
	   }
   }
   
   public void checkWinJob() throws Exception
   {
	   List<Grid> gridList = getGridList();
	   Grid grid = null;
	   User user = null;
	   Date today = Utils.today();
	   ReverseAjaxThread thread = ReverseAjaxThread.getInstance();
		   
	   for( int i=0;i<12;i++){
		   if( i<gridList.size()){
			   grid = gridList.get( i);
		   } else {
			   grid = null;
		   }
				
		   if( grid != null && grid.getIsInPartialWin()){
			   long timeLeft = today.getTime() - grid.getPartialWinStartTime().getTime();
			   timeLeft = grid.getPartialWinSeconds() - (timeLeft/1000);
			   if( timeLeft <= 0){
				   user = grid.getPartialWinUser();
				   
				   Set<ScriptSession> scriptSessionPreviousUserIndex = thread.getUserScriptSessionsIndex( user.getId().toString());
				   Set<ScriptSession> scriptSessionPreviousUserItem = thread.getUserScriptSessionsItem( grid.getId().toString(),user.getId().toString());
				   if( scriptSessionPreviousUserIndex != null){
					   Util utilPreviousUserIndex = new Util(scriptSessionPreviousUserIndex);
					   if( grid.getItem().getType().intValue() == Item.ITEM_TYPE_ONLY_BITCOINS){
						   if( user.getAutoPay()){
							   utilPreviousUserIndex.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "We will send the " + grid.getItem().getName()+" within the next 24 hours." );
						   } else{
							   utilPreviousUserIndex.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "Claim your prize clicking in the Prizes button above." );
						   }
					   }
					   if( grid.getItem().getType().intValue() == Item.ITEM_TYPE_CREDITS_AND_BITCOINS){
						   if( user.getAutoPay()){
							   utilPreviousUserIndex.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "We will send the " + grid.getItem().getName()+" within the next 24 hours. The " + grid.getItem().getCredits() +" credits have already been added to your account." );
						   } else{
							   utilPreviousUserIndex.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "Claim your Bitcoin prize clicking in the Prizes button above. The credits have already been added to your account." );
						   }
					   }
					   if( grid.getItem().getType().intValue() == Item.ITEM_TYPE_ONLY_CREDITS){
						   utilPreviousUserIndex.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "The credits have already been added to your account." );
					   }
				   }
				   if( scriptSessionPreviousUserItem != null){
					   Util utilPreviousUserItem = new Util(scriptSessionPreviousUserItem);
					   if( grid.getItem().getType().intValue() == Item.ITEM_TYPE_ONLY_BITCOINS){
						   if( user.getAutoPay()){
							   utilPreviousUserItem.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "We will send the " + grid.getItem().getName()+" within the next 24 hours." );
						   } else{
							   utilPreviousUserItem.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "Claim your prize clicking in the Prizes button above." );
						   }
					   }
					   if( grid.getItem().getType().intValue() == Item.ITEM_TYPE_CREDITS_AND_BITCOINS){
						   if( user.getAutoPay()){
							   utilPreviousUserItem.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "We will send the " + grid.getItem().getName()+" within the next 24 hours. The " + grid.getItem().getCredits() +" credits have already been added to your account." );
						   } else{
							   utilPreviousUserItem.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "Claim your Bitcoin prize clicking in the Prizes button above. The credits have already been added to your account." );
						   }
					   }
					   if( grid.getItem().getType().intValue() == Item.ITEM_TYPE_ONLY_CREDITS){
						   utilPreviousUserItem.addFunctionCall("playAnimationB", user.getLogin(), "/img/item/"+grid.getItem().getPicture1Url(), "The credits have already been added to your account." );
					   }
				   }
				   
				   
				   manageWin( grid, user, false);
			   }
		   }
	   }
   }
   
   
   public void checkOldJob() throws Exception
   {
	   Criteria criteria = new Criteria();
	   criteria.addNotNull( "finishDate");
	   GridDAO gridDAO = new GridDAO();
	   BoxDAO boxDAO = new BoxDAO();
	   UserDAO userDAO = new UserDAO();
	   GridHistoricDAO gridHistoricDAO = new GridHistoricDAO();
	   List<Grid> gridList = gridDAO.getGridListByCriteria(criteria, null);
	   Date today = Utils.today();
	   Map<Integer,int[]> userDataMap = new HashMap<Integer,int[]>();
	   for( Grid grid:gridList){
		   long timePast = today.getTime() - grid.getFinishDate().getTime();
		   if( timePast >= 120000){ //2 minutos
			   criteria = new Criteria();
			   criteria.addEqualTo("gridId", grid.getId());
			   List<Box> boxList = boxDAO.getBoxListByCriteria(criteria, null);
			   boolean winnerExist = false;
			   for( Box box:boxList){
				   if( box.getUser() != null){
					   int[] userData = userDataMap.get( box.getUser().getId());
					   if( userData == null){
						   userData = new int[4];
						   userData[0] = 0;
						   userData[1] = 0;
						   userData[2] = 0;
						   userData[3] = 0;
						  
					   }
					   if( userData[0] == 0){
						   userData[0] =1;
					   }
					   if( userData[1]==0 && box.getType() == Box.TYPE_WIN){
						   userData[1] =1;
						   winnerExist = true;
					   }
					   if( box.getType() == Box.TYPE_WIN || box.getType() == Box.TYPE_BOUGHT){
						   userData[2] = userData[2] + 1;
						   userData[3] = userData[3] + box.getPrice().intValue();
					   }
					   
					   userDataMap.put(box.getUser().getId(), userData);
					   
				   }
				   boxDAO.deleteBox(box);
			   }
			   
			   PromotionDAO promotionDAO = new PromotionDAO();
			   Promotion returnCreditsPromotion = null;
			   List<Promotion> promotionList = promotionDAO.getPromotionListByCriteria(new Criteria(), null);
			   for( Promotion promotion:promotionList){
					if( today.after( promotion.getStartDate()) && today.before( promotion.getEndDate())){
						if( promotion.getType() == Promotion.PROMOTION_TYPE_RETURN_IF_FAIL){
							returnCreditsPromotion = promotion;
						}
					}
			   }
			   
			   for( Integer userId:userDataMap.keySet()){
				   User user = userDAO.getUserById( userId);
				   
				   int[] userData = userDataMap.get( user.getId());
				   user.setStatisticsPlayedGames( user.getStatisticsPlayedGames() + userData[0]);
				   user.setStatisticsWonGames( user.getStatisticsWonGames() + userData[1]);
				   user.setStatisticsBoughtBoxes( user.getStatisticsBoughtBoxes() + userData[2]);
				   user.setStatisticsUsedCredits(user.getStatisticsUsedCredits() + userData[3]);
				   
				   //Comprueba si hay una promoción de devolución de creditos
				   if( userData[1] == 0 && winnerExist == true){
					   if( returnCreditsPromotion != null){
							user.setCredits( user.getCredits() + userData[3]);
							ReturnCreditsPromotionMailObject returnCreditsPromotionMailObject = new ReturnCreditsPromotionMailObject(  user.getLogin(),  userData[3], Utils.getBaseUrl());
							Mail mail = new Mail( user.getEmail(), "You've got a credit refund on Instantri.ch", returnCreditsPromotionMailObject);
							mail.start();
						}
				   }
				   
				   userDAO.setUser(user);
			   }
			   
			   List<GridHistoric> gridHistoricList = gridHistoricDAO.getGridHistoricListByCriteria(criteria, null);
			   for( GridHistoric gridHistoric:gridHistoricList){
				   gridHistoricDAO.deleteGridHistoric(gridHistoric);
			   }
			   gridDAO.deleteGrid(grid);
			   
			   //Crear un grid igual que empieze en ese momento 
			   if( grid.getType() != Grid.GRID_TYPE_FREE && grid.getType() != Grid.GRID_TYPE_BEGINNER && grid.getItem().getBitcoins()<0.1){
				   createNewGrid(grid);
			   }
		   }
	   }
   }
   
   
   
   private void createNewGrid(Grid oldGrid) throws Exception{
	   Grid grid = null;
	   GridDAO gridDAO = new GridDAO();
	   ItemDAO itemDAO = new ItemDAO();
	   BoxDAO boxDAO = new BoxDAO();
	   Item item = itemDAO.getItemById( oldGrid.getItemId());
	   Date today = Utils.today();
	   Random generator = new Random( Utils.today().getTime());
	   List<Box> boxList = new ArrayList<Box>();

	   double boxPrice = 1; 
				
	   if( oldGrid.getType() == Grid.GRID_TYPE_DOUBLE_OR_NOTHING ){
			if( item.getType() == Item.ITEM_TYPE_ONLY_BITCOINS){
				boxPrice = Math.round( (item.getBitcoins() * 1000) / 2);
			} else if( item.getType() == Item.ITEM_TYPE_ONLY_CREDITS){
				boxPrice = Math.round( item.getCredits() / 2);
			} else if( item.getType() == Item.ITEM_TYPE_CREDITS_AND_BITCOINS){
				boxPrice = Math.round( ((item.getBitcoins() * 1000) + item.getCredits())  / 2);
			}
	   } else{
			if( item.getType() == Item.ITEM_TYPE_ONLY_BITCOINS){
				boxPrice = Math.round( (item.getBitcoins() * 1000) / oldGrid.getBoxes());
			} else if( item.getType() == Item.ITEM_TYPE_ONLY_CREDITS){
				boxPrice = Math.round( item.getCredits()  / oldGrid.getBoxes());
			} else if( item.getType() == Item.ITEM_TYPE_CREDITS_AND_BITCOINS){
				boxPrice = Math.round( ((item.getBitcoins() * 1000) + item.getCredits())  / oldGrid.getBoxes());
			}
	   }
		
	   if( boxPrice < 1){ 
			 boxPrice = 1;
	   }
		
		grid = new Grid();
		grid.setId( null);
		grid.setCreated( today);
		grid.setModified( today);
		grid.setBoughtBoxes(0);
		if( oldGrid.getType().intValue() == Grid.GRID_TYPE_FIXED_PRICE){
			grid.setBoxPrice( oldGrid.getBoxPrice());
		} else if( oldGrid.getType().intValue() == Grid.GRID_TYPE_FREE){
			grid.setBoxPrice( 0d);
		} else{
			grid.setBoxPrice( boxPrice);
		}
		grid.setFinished(false);
		grid.setOngoing(false);
		
		grid.setMoneyWon(0d);
		
		if( oldGrid.getType() == Grid.GRID_TYPE_DOUBLE_OR_NOTHING ){
			grid.setFreeBoxes(2);
			grid.setBoxes(2);
			grid.setWinPos(generator.nextInt(2));
		} else{
			grid.setFreeBoxes(oldGrid.getBoxes());
			grid.setBoxes(oldGrid.getBoxes());
			grid.setWinPos(generator.nextInt(oldGrid.getBoxes()));
		}
		
		if( oldGrid.getType() == Grid.GRID_TYPE_MULTIPRIZE){
			List<Integer> winPosValues = new ArrayList<Integer>();
			winPosValues.add(grid.getWinPos());

			int i = 0;
			while( i<9){
				int value = generator.nextInt(oldGrid.getBoxes());
				while( winPosValues.contains( value)){
					value = generator.nextInt(oldGrid.getBoxes());
				}
				
				winPosValues.add( value);
				i++;
			}
			
			i =0;
			for( int value:winPosValues){
				if( value != grid.getWinPos().intValue()){
					if( i == 0){
						grid.setMultiPrize1_1CreditPos(value);
					}
					if( i == 1){
						grid.setMultiPrize1_2CreditPos(value);
					}
					if( i == 2){
						grid.setMultiPrize1_3CreditPos(value);
					}
					if( i == 3){
						grid.setMultiPrize1_4CreditPos(value);
					}
					if( i == 4){
						grid.setMultiPrize1_5CreditPos(value);
					}
					if( i == 5){
						grid.setMultiPrize2_1CreditPos(value);
					}
					if( i == 6){
						grid.setMultiPrize2_2CreditPos(value);
					}
					if( i == 7){
						grid.setMultiPrize5CreditPos(value);
					}
					if( i == 8){
						grid.setMultiPrize10CreditPos(value);
					}
					
					i++;
				}
			}
			
			
			
			
		}
		
		grid.setItemId(oldGrid.getItemId());
		grid.setItem(item);
		grid.setStartDate(today);
		grid.setFinishDate(null);
		grid.setType(oldGrid.getType());
		if( oldGrid.getType() == Grid.GRID_TYPE_DOUBLE_OR_NOTHING || oldGrid.getType() == Grid.GRID_TYPE_WINNER_IS_FIRST){
			grid.setPartialWinSeconds(3600);
		} else{
			grid.setPartialWinSeconds(300);
		}
		grid.setIsInPartialWin( false);
		grid.setPartialWinPreviousUser(null);
		grid.setPartialWinStartTime( null);
		grid.setPartialWinUser(null);
		grid.setPartialWinUserId( null);
		grid.setVirtualPath( oldGrid.getVirtualPath());
		
		
		//Generamos el texto y el hash para la comprobacion de ganador
		int randomValue = generator.nextInt(5);
		String winPosText = "";
		switch (randomValue){
			case 0: winPosText = "Box=" + grid.getWinPos() + " randomCode = " + generateCode(13);break;
			case 1: winPosText = "prize position = " + grid.getWinPos() + " ignoreThis = " + generateCode(8);break;
			case 2: winPosText = "PoSiTiOn : " + grid.getWinPos() + " CoDe :" + generateCode(10);break;
			case 3: winPosText = "Box With Prize = " + grid.getWinPos() + " Code=" + generateCode(14);break;
			case 4: winPosText = "THE PRIZE IS IN BOX " + grid.getWinPos() + ", RANDOM CODE " + generateCode(7);break;
		}
		
		grid.setWinPosText(winPosText);
		grid.setWinPosHash(Utils.digestMD5(winPosText));
		
		for (int i=0; i<grid.getBoxes();i++){
			Box box = new Box();
			box.setGrid(grid);
			box.setGridId(grid.getId());
			box.setPos( i);
			box.setType( Box.TYPE_FREE);
			box.setPrice( 0d);
			box.setUser( null);
			box.setUserId( null);
			
			boxList.add( box);
		}
		
		boxDAO.setBoxList( boxList);
		
		gridDAO.setGrid(grid);
   }
   
   public void restartServerJob(String code) throws Exception
   {
	   if( code.equals( "Uh76sagHdjhD673KfN")){
		   ReverseAjaxThread thread = ReverseAjaxThread.getInstance();
		   Criteria criteria = new Criteria();
		   criteria.addEqualTo( "name", "serverRestart");
		   ParameterDAO parameterDAO = new ParameterDAO();
		   Parameter parameter = parameterDAO.getParameterByCriteria(criteria);
	
		   if( parameter != null && parameter.getValue().equalsIgnoreCase( "true")){
			   //Se comprueba que no haya ningún juego en partial win
			   List<Grid> gridList = getGridList();
			   boolean hasPartialWin = false;
			   for(Grid grid: gridList){
				   if( grid.getIsInPartialWin() ){
					   hasPartialWin = true;
					   break;
				   }
			   }
			   if( !hasPartialWin){
				   parameterDAO.deleteParameter(parameter);
				   
				   Set<ScriptSession> sessionsAllIndex = thread.getAllScriptSessionsIndex();
				   Set<ScriptSession> sessionsAllItem = thread.getAllScriptSessionsItem();
				   Util utilAllIndex = new Util( sessionsAllIndex);
				   Util utilAllItem = new Util( sessionsAllItem);
				   
				   utilAllIndex.addFunctionCall( "showRestartServerThickbox");
				   utilAllItem.addFunctionCall( "showRestartServerThickbox");
				   
				   Thread.sleep(2000);
				   
				   try {
					   String command = "taskkill /F /IM tomcat5.exe";
					   Process p = Runtime.getRuntime().exec(command);
				
					   InputStream is = p.getInputStream();
					   BufferedReader br = new BufferedReader(new InputStreamReader(is));
					   String line;
					   while ((line = br.readLine()) != null) {
						   System.out.println(line);
					   }
					   is.close();
		
				   } catch (Exception e) {
					   e.printStackTrace();
				   }
			   }
		   }
	   	}
   }
   
   public void restartWarningJob(String code) throws Exception
   {
	   if( code.equals( "Uh76sagHdjhD673KfN")){
		   ReverseAjaxThread thread = ReverseAjaxThread.getInstance();
		   Criteria criteria = new Criteria();
		   criteria.addEqualTo( "name", "serverRestart");
		   ParameterDAO parameterDAO = new ParameterDAO();
		   Parameter parameter = parameterDAO.getParameterByCriteria(criteria);
	
		   if( parameter != null && parameter.getValue().equalsIgnoreCase( "true")){
			   //Se comprueba que no haya ningún juego en partial win
			   List<Grid> gridList = getGridList();
			   boolean hasPartialWin = false;
			   for(Grid grid: gridList){
				   if( grid.getIsInPartialWin() ){
					   hasPartialWin = true;
					   break;
				   }
			   }
			   if( !hasPartialWin){
				   Set<ScriptSession> sessionsAllIndex = thread.getAllScriptSessionsIndex();
				   Set<ScriptSession> sessionsAllItem = thread.getAllScriptSessionsItem();
				   Util utilAllIndex = new Util( sessionsAllIndex);
				   Util utilAllItem = new Util( sessionsAllItem);
				   
				   utilAllIndex.addFunctionCall( "showRestartWarningThickbox");
				   utilAllItem.addFunctionCall( "showRestartWarningThickbox");
			   }
		   }
	   }
   }
   
   
   public void calibrateClockJob(String code) throws Exception
   {
	   if( code.equals( "Uh76sagHdjhD673KfN")){
		   ReverseAjaxThread thread = ReverseAjaxThread.getInstance();
		   Set<ScriptSession> sessionsAllIndex = thread.getAllScriptSessionsIndex();
		   Set<ScriptSession> sessionsAllItem = thread.getAllScriptSessionsItem();
		   Util utilAllIndex = new Util( sessionsAllIndex);
		   Util utilAllItem = new Util( sessionsAllItem);
		   utilAllIndex.addFunctionCall( "calibrateClock",Utils.today().getTime());
		   utilAllItem.addFunctionCall( "calibrateClock",Utils.today().getTime());
	   }
   }
   
   private synchronized void manageWin( Grid grid, User user, boolean isPreviousUser) throws Exception
   {
	   Date today = Utils.today();
	   String htmlAllExceptUser = "";
	   String htmlOnlyUser = "";
	   ReverseAjaxThread thread = ReverseAjaxThread.getInstance();
	   
	   grid.setFinished( true);
	   grid.setIsInPartialWin( false);
	   grid.setOngoing( false);
	   grid.setFinishDate( today);
	   grid.setPartialWinSeconds( 0);
	   grid.setPartialWinStartTime( null);
	   grid.setPartialWinUser( null);
	   
	   GridDAO gridDAO = new GridDAO();
	   gridDAO.setGrid(grid);
	   grid = gridDAO.getGridById( grid.getId());
	   
	   
	   ParameterDAO parameterDAO = new ParameterDAO(); 
	   Criteria criteria = new Criteria();
	   String shortInfo = grid.getId().toString() + true;
	   String hash = Utils.digest( shortInfo);
	   criteria.addLike("value", hash);
	   Parameter parameter = parameterDAO.getParameterByCriteria(criteria);
	   if( parameter != null){
		   shortInfo = grid.getId().toString() + false;
		   hash = Utils.digest( shortInfo);
		   parameter.setValue( hash);
		   parameterDAO.setParameter(parameter);
	   }
	   
	   //Crea el log de final win
	   GridHistoricDAO gridHistoricDAO = new GridHistoricDAO();
	   GridHistoric gridHistoricWin = new GridHistoric();
	   gridHistoricWin.setCreated( Utils.today());
	   gridHistoricWin.setGrid(grid);
	   gridHistoricWin.setGridId(grid.getId());
	   if( isPreviousUser){
		   gridHistoricWin.setType( GridHistoric.FINAL_WIN_PREVIOUS_USER);
	   } else{
		   gridHistoricWin.setType( GridHistoric.FINAL_WIN);   
	   }
	   gridHistoricWin.setValue2(user.getLogin());
	   gridHistoricDAO.setGridHistoric(gridHistoricWin);
	   
	   UserHistoricDAO userHistoricDAO = new UserHistoricDAO();
	   UserHistoric userHistoricWin = new UserHistoric();
	   userHistoricWin.setCreated( Utils.today());
	   userHistoricWin.setUser(user);
	   userHistoricWin.setUserId(user.getId());
	   userHistoricWin.setType( UserHistoric.FINAL_WIN);
	   userHistoricWin.setValue3( grid.getItem().getName());
	   userHistoricDAO.setUserHistoric(userHistoricWin);
	   
	   Set<ScriptSession> sessionsAllIndex = thread.getAllScriptSessionsIndex();
	   Set<ScriptSession> scriptSessionUserIndex = thread.getUserScriptSessionsIndex( user.getId().toString());
	   Set<ScriptSession> sessionsExceptUserIndex = thread.getExceptUserScriptSessionsIndex( user.getId().toString());
	   
	   Set<ScriptSession> sessionsAllItem = thread.getAllScriptSessionsItem( grid.getId().toString());
	   Set<ScriptSession> scriptSessionUserItem = thread.getUserScriptSessionsItem( grid.getId().toString(), user.getId().toString());
	   
	   Util utilAllIndex = new Util( sessionsAllIndex);
	   Util utilUserIndex = new Util( scriptSessionUserIndex);
	   Util utilAllExceptUserIndex = new Util( sessionsExceptUserIndex);
	   
	   Util utilAllItem = new Util( sessionsAllItem);
	   Util utilUserItem = new Util( scriptSessionUserItem);
	   
	   htmlAllExceptUser = generateGridHtml(grid, null, false);
	   htmlOnlyUser = generateGridHtml(grid,user, false);
	   
	   utilAllExceptUserIndex.setValue("dwrTr" + grid.getId(), htmlAllExceptUser);
	   utilUserIndex.setValue("dwrTr" + grid.getId(), htmlOnlyUser);
	   utilAllIndex.addFunctionCall("stopCrono", grid.getId());
	   utilAllIndex.addFunctionCall("hideAllBubblePopups");
	   
	   utilAllItem.setValue("status", "<p><strong>The game is over. The winner is "+user.getLogin()+"</strong>.</p>");
	   utilAllItem.setValue("log", generateLogHtml( grid));
	   utilAllItem.setValue("buyButtonSpan", "<a href='javascript:boxBuyItem("+grid.getId()+",-1);' class='floatRight'><img id='buyButton' class='bb' src='/img/btn_destapar_aleatoria_disabled.png' alt='Open random box' /></a>");
	   utilAllItem.addFunctionCall("stopCronoItem");
	   utilAllItem.addFunctionCall("hideAllBubblePopupsDelayed");
	   utilAllItem.setValue("winPosHash", grid.getWinPosHash());
	   utilAllItem.setValue("winPosText", grid.getWinPosText());
	   
	   //Crea el objeto Win
	   WinDAO winDAO = new WinDAO();
	   BoxDAO boxDAO = new BoxDAO();
	   UserDAO userDAO = new UserDAO();
	   boolean itemWinSaved = false;
	   criteria = new Criteria();
	   criteria.addEqualTo( "userId", user.getId());
	   criteria.addEqualTo( "gridId", grid.getId());
	   List<Box> boughtBoxes = boxDAO.getBoxListByCriteria(criteria, null);
	   Double moneySpent = 0d;
	   for( Box box:boughtBoxes){
		   moneySpent = moneySpent + box.getPrice();
	   }
	   criteria = new Criteria();
	   criteria.addEqualTo( "userId", user.getId());
	   
	   Win win = null;
	   
	   if( grid.getItem().getType() == Item.ITEM_TYPE_CREDITS_AND_BITCOINS || grid.getItem().getType() == Item.ITEM_TYPE_ONLY_BITCOINS){
		   win = new Win();
		   
		   win.setCreated( today);
		   win.setModified( today);
		   win.setBoughtBoxes(boughtBoxes.size());
		   win.setMoneySpent(moneySpent);
		   win.setItem(grid.getItem());
		   win.setItemId(grid.getItem().getId());
		   win.setItemSent( false);
		   
		   win.setUser(user);
		   win.setUserId(user.getId());
		   win.setIsOnlyCredits( false);
		   win.setTransactionId(null);
		   win.setGridType( grid.getType());
		   
		   if( user.getAutoPay()){
			   win.setDeliveryRequested( true);
			   win.setBitcoinAddress( user.getBitcoinAddress());
		   }else{
			   win.setDeliveryRequested( false);
		   }
		   
		   winDAO.setWin(win);
		   itemWinSaved = true;
	   }
	   
	   if( grid.getItem().getType() == Item.ITEM_TYPE_ONLY_CREDITS || grid.getItem().getType() == Item.ITEM_TYPE_CREDITS_AND_BITCOINS){
		   win = new Win();
		   if( itemWinSaved == false){
			   win.setBoughtBoxes(boughtBoxes.size());
			   win.setMoneySpent(moneySpent);
		   } else{
			   win.setBoughtBoxes(0);
			   win.setMoneySpent(0d);
		   }
		   win.setCreated( today);
		   win.setModified( today);
		   win.setItem(grid.getItem());
		   win.setItemId(grid.getItem().getId());
		   win.setItemSent( true);
		   win.setDeliveryRequested( false);
		   win.setGridType(grid.getType());
		   win.setUser(user);
		   win.setUserId(user.getId());
		   win.setIsOnlyCredits( true);
		   win.setTransactionId(null);
		   winDAO.setWin(win);
		   
		   user = userDAO.getUserById( user.getId());
		   user.setCredits(user.getCredits() + grid.getItem().getCredits());
		   userDAO.setUser(user);
		   if(utilUserIndex != null){
			   utilUserIndex.setValue("credits" , user.getCredits());
			   utilUserIndex.addFunctionCall("trHighlightItem", "credits");
			   
			   utilUserItem.setValue("credits" , user.getCredits());
			   utilUserItem.addFunctionCall("trHighlightItem", "credits");
		   }
	   }

	   criteria = new Criteria();
	   criteria.addEqualTo("pos", grid.getWinPos());
	   criteria.addEqualTo("gridId", grid.getId());
	   Box box = boxDAO.getBoxByCriteria(criteria);
	   box.setType( Box.TYPE_WIN);
	   boxDAO.setBox(box);
	   
	   if( user.getIsBeginner()){
		   user.setIsBeginner( false);
		   userDAO.setUser(user);
	   }

	   UserWinMailObject userWinMailObject = new UserWinMailObject( user.getLogin(), grid.getItem(), win, user.getAutoPay(), Utils.getBaseUrl());
	   Mail mail = new Mail( user.getEmail(), "You've won a prize on Instantri.ch", userWinMailObject);
	   mail.start();
   }
   
   public synchronized void manageBuyCredits( User user, Integer credits) throws Exception
   {
	   ReverseAjaxThread thread = ReverseAjaxThread.getInstance();
	   
	   Set<ScriptSession> scriptSessionUserIndex = thread.getUserScriptSessionsIndex( user.getId().toString());
	   Util utilUserIndex = new Util( scriptSessionUserIndex);
	   
	   if(utilUserIndex != null){
		   utilUserIndex.setValue("credits" , user.getCredits());
		   utilUserIndex.addFunctionCall("trHighlightItem", "credits");
	   }
   }
   
   private String generateGridHtml( Grid grid, User user, boolean withMainDiv) throws Exception
   {
	   Date startDate = grid.getStartDate();
	   Date today = Utils.today();
	   String output = "";
	   
	   if( withMainDiv){
		   output = output + "<div class='cajaProducto' id='dwrTr" + grid.getId() + "'>";
	   }
	   output = output + "<h2><a href='" + grid.getVirtualPath()+"'>"+grid.getItem().getName()+"</a></h2>";
	   
	   if( grid.getType().intValue() != Grid.GRID_TYPE_REGULAR){
		   output = output + "<a href='#' class='showDetails' rel='.info_00"+ grid.getType().intValue()+"' style='display:block;'>";
	   }
	   if( grid.getType().intValue() == Grid.GRID_TYPE_FIXED_PRICE){
			output = output + "<img src='/img/ico_sorteo_preciounico.png' alt='Fixed price game' class='tipoSorteo' />";
	   } else if( grid.getType().intValue() == Grid.GRID_TYPE_BEGINNER){
			output = output + "<img src='/img/ico_sorteo_principiantes.png' alt='Begginers game' class='tipoSorteo' />";
	   } else if( grid.getType().intValue() == Grid.GRID_TYPE_WINNER_IS_FIRST){
			output = output + "<img src='/img/ico_sorteo_elprimero.png' alt='Fast game' class='tipoSorteo' />";
	   } else if( grid.getType().intValue() == Grid.GRID_TYPE_DOUBLE_OR_NOTHING){
			output = output + "<img src='/img/ico_sorteo_pistas.png' alt='Double or nothing' class='tipoSorteo' />";
	   } else if( grid.getType().intValue() == Grid.GRID_TYPE_FREE){
			output = output + "<img src='/img/ico_sorteo_gratis.png' alt='Free game' class='tipoSorteo' />";
	   } else if( grid.getType().intValue() == Grid.GRID_TYPE_MULTIPRIZE){
			output = output + "<img src='/img/ico_sorteo_multipremio.png' alt='Multiprize game' class='tipoSorteo' />";
	   }
	   
	   if( grid.getType().intValue() != Grid.GRID_TYPE_REGULAR){
		   output = output + "</a>";
	   }
	   
	   output = output + "<a href='" + grid.getVirtualPath()+"'>";
	   output = output + "<div class='mostrarDetalle'>";
	   output = output + "Show details";
	   output = output + "</div>";
	   output = output + "<img src='/img/item/" + grid.getItem().getPicture1Url() + "' alt='"+grid.getItem().getName()+"' class='imgProducto' onmouseover='javascript:showDetailOn(this);' onmouseout='javascript:showDetailOff(this);'/>";
	   output = output + "</a>";
	   output = output + "<span class='creditos' id='boxPrice_"+grid.getId()+"'>"+new DecimalFormat( "0").format(grid.getBoxPrice())+"</span>";
	   output = output + "<span class='cajasLibres' id='freeBoxes_"+grid.getId()+"'><strong>"+grid.getFreeBoxes()+"</strong> Unopened boxes</span>";
	   output = output + "<div class='texto' id='log_"+grid.getId()+"'>";
	   if( !grid.getOngoing() && !grid.getFinished()){
		   GregorianCalendar todayCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
		   GregorianCalendar startDateCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
		   todayCalendar.setTime( today);
		   startDateCalendar.setTime( startDate);
		   if( todayCalendar.get( Calendar.YEAR) == startDateCalendar.get( Calendar.YEAR) && todayCalendar.get( Calendar.MONTH) == startDateCalendar.get( Calendar.MONTH) && todayCalendar.get( Calendar.DAY_OF_MONTH) == startDateCalendar.get( Calendar.DAY_OF_MONTH)){
			   output = output + "<div style='font-size:16px;text-align:center;color:#666;'>Starts at " + Utils.getTimeShort(grid.getStartDate()) + "</div>";
		   } else{
			   
			   output = output + "<div style='font-size:16px;text-align:center;color:#666;'>Starts on " + Utils.getDate(grid.getStartDate(), 5) + " at " + Utils.getTimeShort(grid.getStartDate())+"</div>";
		   }
	   } else{
		   if( grid.getIsInPartialWin() == null || grid.getIsInPartialWin() == false){
			   output = output + generateLogHtml(grid);
		   } else{
			   long timeLeft = today.getTime() - grid.getPartialWinStartTime().getTime();
			   timeLeft = grid.getPartialWinSeconds() - (timeLeft/1000);
			   if(timeLeft < 0){
				   timeLeft =0;
			   }
			   int min = (int)timeLeft / 60 ;
			   int sec = (int)timeLeft % 60;
			   
			   output = output +  "<p><strong>" + grid.getPartialWinUser().getLogin() +" has found the prize!</strong><br />He will get it if nobody opens a box in:</p>";
			   output = output +  "<div class='reloj' id='crono"+grid.getId()+"'>"+new DecimalFormat( "00").format(min)+":"+new DecimalFormat( "00").format(sec)+"</div>";
		   }
	   }
	   output = output + "</div>";
	   if( grid.getOngoing()){
		   output = output + "<span id='buyButtonSpan_"+grid.getId()+"'>";
		   if( grid.getIsInPartialWin() != null && grid.getIsInPartialWin() == true && user!= null && user.getId().intValue() == grid.getPartialWinUser().getId().intValue()){
			   output = output + "<a href='javascript:boxBuy("+grid.getId()+"," + new DecimalFormat( "0").format(grid.getBoxPrice()) + ",-1);' ><img src='/img/btn_destapar_disabled.png' alt='Open box' class='bb destaparCaja' id='buyButton"+grid.getId()+"'/></a>";
		   }else{
			   output = output + "<a href='javascript:boxBuy("+grid.getId()+"," + new DecimalFormat( "0").format(grid.getBoxPrice()) + ",-1);' ><img src='/img/btn_destapar.png' alt='Open box' class='bb destaparCaja' id='buyButton"+grid.getId()+"'/></a>";
		   }
		   output = output + "</span>";
	   }
	   if( withMainDiv){
		   output = output + "</div>";
	   }
	   
	   return output;
   }
   
   private String generateLogHtml(Grid grid) throws Exception 
   {
	   Criteria criteria = new Criteria();
	   GridHistoricDAO gridHistoricDAO = new GridHistoricDAO();
	   Date today = Utils.today(); 
	   criteria.addEqualTo( "gridId", grid.getId());
	   List<GridHistoric> gridHistoricList = gridHistoricDAO.getGridHistoricListByCriteriaAndRange(criteria, "created", 0, 6);
	   String historicHtml = "<ul>";
	   
	   Collections.sort( gridHistoricList, new Comparator<GridHistoric>() {
          public int compare( GridHistoric g1, GridHistoric g2) {
              if( g1.getCreated().before(g2.getCreated())) return 1;
              if( g1.getCreated().after(g2.getCreated())) return -1;
              return g2.getType().compareTo(g1.getType());
          }
	   });
	   
	   for( int j=gridHistoricList.size() - 1;j>=0;j--){
		   GridHistoric gh = gridHistoricList.get( j);
		   String time = null;
		   if( today.getTime() - gh.getCreated().getTime() > 86400000) {// más de un día
			   int days = Utils.getDaysFrom( gh.getCreated(), today);
			   time = days + (days == 1?" day ago":" days ago"); 
		   } else{
			   time = Utils.getTime( gh.getCreated());
		   }
		   
		   
		   if(gh.getType()== GridHistoric.START){
			   historicHtml=historicHtml + "<li><strong>"+ time + "</strong> The game is on</li>";
		   }
		   if(gh.getType()== GridHistoric.BUY_BOX){
			   historicHtml=historicHtml + "<li><strong>"+ time + "</strong> "+Utils.briefString(gh.getValue2(), 10)+" opened box "+new DecimalFormat( "0").format(gh.getValue1())+"</li>";
		   }
		   if(gh.getType()== GridHistoric.PARTIAL_WIN){
			   historicHtml=historicHtml + "<li><strong>"+ time + "</strong><span class='destacado'> "+Utils.briefString(gh.getValue2(), 10)+ " found the prize</span></li>";
		   }
		   if(gh.getType()== GridHistoric.FINAL_WIN){
			   historicHtml=historicHtml + "<li><span class='destacado'> "+Utils.briefString(gh.getValue2(), 10)+" won the prize</span></li>";
		   }
		   if(gh.getType()== GridHistoric.NO_WIN){
			   if( grid.getType() == Grid.GRID_TYPE_DOUBLE_OR_NOTHING ||  grid.getType() == Grid.GRID_TYPE_FREE){
				   historicHtml=historicHtml + "<li><strong>"+ time + "</strong><span class='destacado'> There is no winner.</span></li>";
			   } else{
				   historicHtml=historicHtml + "<li><strong>"+ time + "</strong><span class='destacado'> There is no winner.</span></li><li><span class='destacado'> All credits will be refund</span></li>";
			   }
		   }
		   if(gh.getType()== GridHistoric.FINAL_WIN_PREVIOUS_USER){
			   historicHtml=historicHtml + "<li><strong>"+ time + "</strong><span class='destacado'> Nobody found the prize box.</span></li><li><span class='destacado'> <strong>"+Utils.briefString(gh.getValue2(), 10)+"</strong>, gets it, he was the last one who found it</span></li>";
		   }
		   if(gh.getType()== GridHistoric.FIND_MULTIPRIZE){
			   historicHtml=historicHtml + "<li><span class='destacado'> "+Utils.briefString(gh.getValue3(), 10)+" found "+new DecimalFormat( "0").format(gh.getValue1())+" credits in box " +gh.getValue2()+ "</span></li>";
		   }
	   }
	   
	   historicHtml=historicHtml + "</ul>";
	   
	   return historicHtml ;
   }
   
   private String generateItemGridHtml(Grid grid ) throws Exception 
   {
	   BoxDAO boxDAO = new BoxDAO();
	   Criteria criteria = new Criteria();
	   criteria.addEqualTo("gridId", grid.getId());
	   List<Box> boxList = boxDAO.getBoxListByCriteria(criteria, "pos");
	   String html = "";
	   int partialWinPos = -1;
	   
	   if( grid.getIsInPartialWin() || grid.getFinished()){
		   partialWinPos = grid.getWinPos();
	   }

	   for(int i=boxList.size()-1;i>=0;i--){
		   Box box = boxList.get(i);
		   if( box.getPos().intValue() == partialWinPos){
			   html = html + " <li><span class='cajaPremiada bb' id='b"+box.getPos()+"'><strong>" + box.getPos() + "</strong></span></li>";
		   }else{
			   if( box.getType() == Box.TYPE_FREE){
				   html = html + " <li><span id='b"+box.getPos()+"' class='bb'><a href='javascript:boxBuyItem("+grid.getId()+","+box.getPos()+");'><strong>" + box.getPos() + "</strong></a></li>";
			   } else if( box.getType() == Box.TYPE_BOUGHT || box.getType() == Box.TYPE_WIN){
				   if( grid.getType() == Grid.GRID_TYPE_MULTIPRIZE){
					   if( box.getPos().intValue() == grid.getMultiPrize1_1CreditPos() || box.getPos().intValue() == grid.getMultiPrize1_2CreditPos() || box.getPos().intValue() == grid.getMultiPrize1_3CreditPos() || box.getPos().intValue() == grid.getMultiPrize1_4CreditPos() || box.getPos().intValue() == grid.getMultiPrize1_5CreditPos() ){
						   html = html + " <li><span class='cajaPremiada1 bb' id='b"+box.getPos()+"'><strong>" + box.getPos() + "</strong></span></li>";
					   } else if( box.getPos().intValue() == grid.getMultiPrize2_1CreditPos() || box.getPos().intValue() == grid.getMultiPrize2_2CreditPos()){
						   html = html + " <li><span class='cajaPremiada2 bb' id='b"+box.getPos()+"'><strong>" + box.getPos() + "</strong></span></li>";
					   } else if( box.getPos().intValue() == grid.getMultiPrize5CreditPos()){
						   html = html + " <li><span class='cajaPremiada5 bb' id='b"+box.getPos()+"'><strong>" + box.getPos() + "</strong></span></li>";
					   } else if( box.getPos().intValue() == grid.getMultiPrize10CreditPos()){
						   html = html + " <li><span class='cajaPremiada10 bb' id='b"+box.getPos()+"'><strong>" + box.getPos() + "</strong></span></li>";
					   }else{
						   html = html + " <li><span class='cajaAbierta bb' id='b"+box.getPos()+"'><strong>" + box.getPos() + "</strong></span></li>";
					   }
				   } else{
					   html = html + " <li><span class='cajaAbierta bb' id='b"+box.getPos()+"'><strong>" + box.getPos() + "</strong></span></li>";
				   }
			   }
		   }
	   }
	   
	   return html;
   }
   
   private List<Grid> getGridList() throws Exception{
	   GridDAO gridDAO = new GridDAO();
	   Criteria criteria = new Criteria();
	   //criteria.addEqualTo( "finished", false);
	   List<Grid> gridList = gridDAO.getGridListByCriteriaAndRange(criteria, "-startDate", 0, 12);
		
	   Collections.sort( gridList, new Comparator<Grid>() {
          public int compare( Grid g1, Grid g2) {
        	  
        	  int value1 = 0;
        	  int value2 = 0;
        	  
        	  if( g1.getItem().getType() == Item.ITEM_TYPE_ONLY_BITCOINS){
        		  value1 = new Double(g1.getItem().getBitcoins()*1000d).intValue();
        	  } else if( g1.getItem().getType() == Item.ITEM_TYPE_ONLY_CREDITS){
        		  value1 = g1.getItem().getCredits();
        	  } else if( g1.getItem().getType() == Item.ITEM_TYPE_CREDITS_AND_BITCOINS){
        		  value1 = g1.getItem().getCredits();
        		  value1 = value1 + new Double(g1.getItem().getBitcoins()*1000d).intValue();
        	  }
        	  
        	  if( g2.getItem().getType() == Item.ITEM_TYPE_ONLY_BITCOINS){
        		  value2 = new Double(g2.getItem().getBitcoins()*1000d).intValue();
        	  } else if( g2.getItem().getType() == Item.ITEM_TYPE_ONLY_CREDITS){
        		  value2 = g2.getItem().getCredits();
        	  } else if( g2.getItem().getType() == Item.ITEM_TYPE_CREDITS_AND_BITCOINS){
        		  value2 = g2.getItem().getCredits();
        		  value2 = value2 + new Double(g2.getItem().getBitcoins()*1000d).intValue();
        	  }
        	  
              if( value1>value2) return -1;
              if(value1<value2) return 1;
              return 0;
          }
	   });
	   
	   return gridList;
   }
   
   private List<GridData> generateGridTable(User user) throws Exception
   {
	   List<GridData> result =  new ArrayList<GridData>(); 
	   String shortInfo = "";
	   String html = "";
	  
	   List<Grid> gridList = getGridList();
					
	   for( Grid grid:gridList){
		   GridData gridData = new GridData();
		   html = "";
		   shortInfo = "";
		  
		   html = generateGridHtml(grid, user, true);
		   shortInfo = grid.getId().toString() + grid.getOngoing();
		   gridData.setIsInPartialWin( grid.getIsInPartialWin());
		   gridData.setId(grid.getId());
		   if( grid.getIsInPartialWin()){
			   Date today = Utils.today();
			   long timeLeft = today.getTime() - grid.getPartialWinStartTime().getTime();
			   timeLeft = grid.getPartialWinSeconds() - (timeLeft/1000);
			   if(timeLeft < 0){
				   timeLeft =0;
			   }
			   gridData.setPartialWinSeconds( (int)timeLeft);
			   gridData.setPartialWinUserId(grid.getPartialWinUser().getId());
		   } 
		   
		   gridData.setGridHtml(html);
		   gridData.setShortInfo(shortInfo);
			
		   result.add( gridData);
	   }
		
	   return result;
   }
   
   private String generateCode(int length) 
	{
		String 	chars 	= "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ.-=?¿)(/&%$!<>*+[]{}:;,#@";
		String 	code	= "";
		
		
		while( code.length() < length){
	      code = code + chars.charAt( ( int) Math.round( Math.random() * ( chars.length() - 1)));
		}
		
		return code;
	}
}

	
