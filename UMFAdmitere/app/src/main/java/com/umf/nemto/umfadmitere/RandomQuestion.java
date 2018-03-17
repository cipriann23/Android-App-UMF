package com.umf.nemto.umfadmitere;

/**
 * Created by nemto on 02.08.2017.
 */


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.content.Intent;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

public class RandomQuestion extends Activity {


    Integer answer;
    String category, year, theme_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setTheme(R.style.AppThemeDark);
        // Get the Intent that started this activity and extract the string

        Intent intent = getIntent();
        theme_set = intent.getStringExtra("theme");
        if (theme_set.equals("dark")) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_random_question);

        category = intent.getStringExtra("category");
        year = intent.getStringExtra("year");

        TextView textView = (TextView) findViewById(R.id.textView);
        RadioButton radio1 = (RadioButton) findViewById(R.id.radioButton1);
        RadioButton radio2 = (RadioButton) findViewById(R.id.radioButton2);
        RadioButton radio3 = (RadioButton) findViewById(R.id.radioButton3);
        RadioButton radio4 = (RadioButton) findViewById(R.id.radioButton4);
        RadioButton radio5 = (RadioButton) findViewById(R.id.radioButton5);

        if(isOnline()) {

            try {
                String[] output = new BackgroundWorker(this).execute(year, category).get();
                int min = 0;

                try {
                    min = output.length;
                }catch (Exception e) {
                    e.printStackTrace();
                }


                if(min > 0){

                    byte[] data0 = Base64.decode(output[0], Base64.DEFAULT);
                    byte[] data1 = Base64.decode(output[1], Base64.DEFAULT);
                    byte[] data2 = Base64.decode(output[2], Base64.DEFAULT);
                    byte[] data3 = Base64.decode(output[3], Base64.DEFAULT);
                    byte[] data4 = Base64.decode(output[4], Base64.DEFAULT);
                    byte[] data5 = Base64.decode(output[5], Base64.DEFAULT);
                    byte[] data6 = Base64.decode(output[6], Base64.DEFAULT);
                    try {
                        String text0 = new String(data0, "UTF-8");
                        textView.setText(text0);

                        String text1 = new String(data1, "UTF-8");
                        String text2 = new String(data2, "UTF-8");
                        String text3 = new String(data3, "UTF-8");
                        String text4 = new String(data4, "UTF-8");
                        String text5 = new String(data5, "UTF-8");
                        String text6 = new String(data6, "UTF-8");

                        radio1.setText(text1);
                        radio2.setText(text2);
                        radio3.setText(text3);
                        radio4.setText(text4);
                        radio5.setText(text5);
                        answer = Integer.parseInt(text6);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }


                    int max = output.length;
                    if (max > 7) {

                        ImageView image = (ImageView) findViewById(R.id.imageView2);
                        byte[] decodedString = Base64.decode(output[7], Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        image.setImageBitmap(decodedByte);
                    }
                }else{
                    String[] dataOff = dataOffline(category);
                    textView.setText(dataOff[0]);
                    radio1.setText(dataOff[1]);
                    radio2.setText(dataOff[2]);
                    radio3.setText(dataOff[3]);
                    radio4.setText(dataOff[4]);
                    radio5.setText(dataOff[5]);
                    answer = Integer.parseInt(dataOff[6]);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }else{
            String[] dataOff = dataOffline(category);
            textView.setText(dataOff[0]);
            radio1.setText(dataOff[1]);
            radio2.setText(dataOff[2]);
            radio3.setText(dataOff[3]);
            radio4.setText(dataOff[4]);
            radio5.setText(dataOff[5]);
            answer = Integer.parseInt(dataOff[6]);
        }

        ///////--------------------------------

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public String[] dataOffline(String category){

        String[] bio1 = new String[]{"Prelungirile citoplasmei sunt: ","peudopode ","microvili ","cili","desmozomi   ","toate răspunsurile sunt corecte   ","5"};
        String[] bio2 = new String[]{"Ribozomii au rol în:","excreția unor substanțe celulare","fosforilarea oxidativă","digestia fragmentelor celulare","sinteza proteică","Diviziunea celulară","4"};
        String[] bio3 = new String[]{"Lizozomii sunt:","organite bogate în ribonucleoproteine","un sistem canalicular","o rețea de citomembrane","corpusculi sferici","un sistem membranar format din cisterne alungite","4"};
        String[] bio4 = new String[]{"Organitele celulare specifice sunt:","neurofibrilele","lizozomii","centrozomul","mitocondriile","ribozomii","1"};
        String[] bio5 = new String[]{"Membrana celulară nu poate fi traversată prin difuziune de către:","hormoni steroizi","etanol","glucoză","uree","O₂","3"};
        String[] bio6 = new String[]{"Transportul activ:","asigură deplasarea moleculelor conform gradientelor de concentrație","poate fi primar și secundar","nu necesită consum de energie","asigură deplasare apei prin membrană","necesită prezența unor structuri membranare numite canale","2"};
        String[] bio7 = new String[]{"Centrozomul are rol în:","diviziunea celulară","excreția unor substanțe celulare","digestia unor substanțe celulare","sinteza proteică","fosforilarea oxidativă","1"};
        String[] bio8 = new String[]{"Ribozomii:","se mai numesc corpusculii lui Palade","sunt organite bogate în ribonucleoproteine","sunt liberi sau atașați reticulului endoplasmic","au forma unor granule ovale sau rotunde","toate răspunsurile sunt corecte","5"};
        String[] bio9 = new String[]{"Panta ascendentă a potențialului de acțiune:","se numește repolarizare","se datorează ieșirii K+ din celulă","se datorează intrării K+ în celulă","se datorează intrării Na+ în celulă","se datorează ieșirii Na+ din celulă","4"};
        String[] bio10 = new String[]{"Panta descendentă a potențialului de acțiune:","se datorează intrării K+ în celulă","se numește depolarizare","se datorează ieșirii K+ din celulă","se datorează ieșirii Na+ din celulă","se datorează intrării Na+ în celulă","3"};
        String[] bio11 = new String[]{"Epiteliul traheal este de tip:","exocrin","epitelial pseudostratificat","conjunctiv moale","conjunctiv semidur","senzorial","2"};
        String[] bio12 = new String[]{"Țesutul cartilaginos fibros este întâlnit în:","cartilajele costale","laringe","pavilionul urechii","trahee","meniscurile articulare","5"};
        String[] bio13 = new String[]{"Țesutul cartilaginos elastic este întâlnit în:","discurile intervertebrale","meniscurile articulare","pavilionul urechii","cartilajele costale","tendoane","3"};
        String[] bio14 = new String[]{"Diafizele oaselor lungi sunt formate din țesut:","cartilaginos fibros","osos trabecular","conjunctiv fibros","osos compact","pavimentos keratinizat","4"};
        String[] bio15 = new String[]{"Țesutul conjunctiv reticulat este întâlnit în:","tendoane","aponevroze","tunica medie a arterelor","pancreas","splină","5"};
        String[] bio16 = new String[]{"Sângele este un țesut:","senzorial","conjunctiv fluid","cartilaginos","conjunctiv semidur","reticulat","2"};
        String[] bio17 = new String[]{"Pancreasul este alcătuit din țesut:","glandular de tip mixt","glandular endocrin de tip folicular","glandular exocrin simplu","glandular exocrin tubular","glandular exocrin acinos","1"};
        String[] bio18 = new String[]{"Inițial toate celulele au formă:","cilindrică","cubică","globuloasă","stelată","fusiformă","3"};
        String[] bio19 = new String[]{"Dimensiunea fibrei musculare striate este:","5-15 µ","7,5 µ","150-200 µ","5-15 cm","150-200 cm      ","4"};
        String[] bio20 = new String[]{"Identificați afirmația FALSĂ:","excitațiile propagate pe căile senzitive determină, în ariile corticale, formarea de senzații","în hipoderm sa află bulbii firului de păr, glomerulii glandelor sudoripare, papilele dermice și corpusculii Vater-Pacini","receptorii termici sunt terminații nervoase libere, cu diametrul mic și nemielinizate","fusurile neuromusculare sunt diseminate printre fibrele musculare striate, care sunt timulate de tensiunea dezvoltată în timpul contracției musculare ","mugurii gustativi au formă ovoidală","2"};
        String[] bio21 = new String[]{"identificați afirmația FALSĂ:","cei mai mulți dintre mugurii gustativi pot fi stimulați de doi sau mai mulți stimuli gustativi și chiar și de unii stimuli gustativi care nu intră în categoria celor primari ","tunica externă ( vasculară) a globului ocular este fibroasă și formată din două porțiuni inegale: posterior se află sclerotica, iar anterior, corneea  ","funcția principală a analizatorului vizual este perceperea luminozității, formei și culorii obiectelor din lumea înconjurătoare ","urechea externă și urechea medie nu au nici o relație cu aparatul vestibular ","otita externă este un termen general prin care se denumește orice infecție a urechii externe ( micotică, bacteriană, virală) ","2"};
        String[] bio22 = new String[]{"Identificați afirmația corectă:","receptorul unui analizator este o formațiune nespecializată, care poate percepe o anumită formă  de energie din mediul extern sau intern, sub formă stimuli","pielea este alcătuită de la profunzime pre suprafață din trei straturi : epidermul, dermul și hipodermul ","suprafața câmpului receptor este în raport invers proporțional cu densitatea reeptorilor din regiune","corpusculii neurotendinoși Golgi sunt situați la joncțiunea mușchi-os","celulele bipolare din mucoasa olfactivă au un axon scurt și gros, care pleacă de la polul bazal   ","3"};
        String[] bio23 = new String[]{"Identificați afirmația FALSĂ:","papilele filiforme nu au muguri gustativi ","gustul amar se percepe la baza limbii ","vederea are o considerabilă importanță fiziologică în orientarea în spațiu, menținerea echilibrului și a tonusului cortical ","cristalinul are forma unei lentile biconvexe, transparente, localizată între iris și umoarea apoasă ","urechea externă cuprinde pavilionul și conductul auditiv extern ","4"};
        String[] bio24 = new String[]{"Identificați afirmația FALSĂ :","urechea medie este o cavitate pneumatică săpată în stânca temporalului","în centrul organului Corti se găsește un spațiu triunghiular numit tunelul Corti","de la nucleii vestibulari din bulb pleacă fasciculul vestibulo-spinal spre măduvă ( controlează tonusul muscular )","spațiul cuprins cu privirea se numește câmp vizual","aria vizuală primară s întinde mai ales pe fața laterală a lobilor occipitali, de o parte și de alta a scizurii calcarine","5"};
        String[] bio25 = new String[]{"Identificați afirmația corectă:","aproximativ 8% din populația masculină suferă de daltonism ","punctul cel mai apropiat de ochi la care vedem clar un obiect, cu efort acomodativ minim se numește punct proxim","la polul apical al celulelor gustativ sosesc terminații nervoase ale nervilor facial, glosofaringian și vag ","receptorii analizatorului olfactiv sunt chemoreceptori care ocupă partea antero-superioară a foselor nazale ","inervația senzitivă a fusurilor neuromusculare este asigurată de axonul neuronilor senzitiv din ganglionul spinal ","1"};
        String[] bio26 = new String[]{"Identificați afirmația FALSĂ:","receptorii tactili fac parte din categoria mecanoreceptorilor , fiind stimulați de deformări mecanice ","corpusculii Vater-Pacini din periost și articulații nu sunt identici cu cei din piele ","corpusculii Ruffini se află în stratul superficial al capsulei articulare și recepționează poziția și mișcările din articulații","simțul mirosului este slab dezvoltat la om, comparativ cu unele animale","în structura mugurilor gustativi se găsesc celule senzoriale, care prezintă la polul apical un microvil ","2"};
        String[] bio27 = new String[]{"Identificați afirmația FALSĂ:","globul ocular, de formă aproximativ sferică este situat în orbită ","bastonașele sunt adaptate pentru vederea nocturnă, la lumină slabă","acomodarea reprezintă variația puterii de refracție a cristalinului în raport cu distanța la care privim un obiect","labirintul osos este format din vestibulul osos, canalele semicirculare osoase și melcul osos ","urechea umană poate percepe undele sonore, repetate într-o anumită ordine ( zgomote) sau succedându-se neregulat ( sunete)","5"};
        String[] bio28 = new String[]{"Identificați afirmația corectă:","labirintul osos este format dintr-un sistem de camere situate în interiorul labirintului membranos","receptorii maculari sunt stimulați electric de către otolite ","cele trei canale semicirculare osoase se află în planuri perpendiculare unul pe celălalt","reducerea vederii diurne se numește nictalopie, iar a celei nocturne hemeralopie ","celulele bipolare din mucoasa olfactivă au și rolul de deutoneuron ","3"};
        String[] bio29 = new String[]{"Identificați afirmația falsă:","terminațiile nervoase libere se ramifică în toată grosimea capsulei articulare și transmit sensibilitatea dureroasă articulară","dispunerea în serie a fibrelor intrafusale face ca întinderea fibrelor intrafusale să determine și întinderea celor extrafusale ","receptorii tactili sunt localizați în derm și sunt mai numeroși în tegumentele fără păr ","corpusculii Ruffini sunt considerați și receptori pentru cald","epidermul este un epiteliu pluristratificat keratinizat ","2"};
        String[] bio30 = new String[]{"Identificați afirmația falsă:","receptorii pentru durere sunt, în principal terminații nervoase libere ","termoreceptorii sunt răspândiți peste tot în derm, fiind mai numeroși pe buze și în mucoasa nazală","relaxarea musculară  este prevenită prin întinderea și activarea fusurilor, care la rândul lor declanșează o contracție reflexă ","calea olfactivă  nu are legături directe cu talamusul ","receptorii analizatorului olfactiv sunt chemoreceptori, reprezentați de muguri gustativi","2"};
        String[] bio31 = new String[]{"Identificați afirmația FALSĂ:","axonii neuronilor multipolari din bulbul olfactivi  formează tractul olfactiv care în final se proiectează pe fața medială a lobului temporal ","pata oarbă este situată medial și superior de pata galbenă","cristalinul nu conține vase sangvine, nutriția sa făcându-se prin difuziune, de la vasele proceselor ciliare ","neuronul întâi al căii optice se află la nivelul celulelor bipolare din retină","urechea medie conține în interiorul său un lanț articulat de oscioare: ciocan, nicovală și scăriță","2"};
        String[] bio32 = new String[]{"Identificați afirmația corectă:","celulele senzoriale de la nivelul organului Corti transformă energia  electrică a sunetelor în impuls nervos","depolarizarea celulelor senzoriale scad frecvența potențialelor de acțiune, iar hiperpolarizările o cresc ","la polul apical al celulelor auditive se găsesc cili auditivi, care pătrund în membrana reticulată secretată de celulele de susținere","procesul de fuzionare a imaginilor începe la nivelul corpilor geniculați mediali ","corpurile care reflectă toate radiațiile luminoase apar negre ","3"};
        String[] bio33 = new String[]{"Identificați afirmația FALSĂ:","acomodarea este un act reflex, reglat de centrii corticali și de coliculii cvadrigemeni superiori","retina este sensibilă la radiațiile electromagnetice cu lungimea de undă cuprinsă între 390 și 770 nm","celulele cu conuri sunt celule nervoase modificate","corpul ciliar se află imediat îndărătul orei serrata","identitatea receptorii pentru gust este încă incomplet cunoscută  ","4"};
        String[] bio34 = new String[]{"Identificați afirmația FALSĂ:","nervii olfactivi străbat lama ciuruită a etmoidului și se termină în bulbul olfactiv ","impulsul nervos se transmite neuronului α, ceea ce duce la contracția mușchiului ","fusurile neuromusculare au inervație senzitivă și motorie","corpusculii Pacini se adaptează foarte rapid și recepționează vibrațiile ","receptorii pentru cald îi depășesc numeric pe cei pentru rece ","5"};
        String[] bio35 = new String[]{"Identificați afirmația FALSĂ:","receptorii cu localizarea în partea superioară a dermului recepționează atingerea ","în corpusculul Golgi pătrund 1-3 fibre nervoase, care sunt stimulate de contracția puternică a tendonului ","dendrita celulelor bipolare olfactive, prevăzută cu cili ","protoneuronul căii gustative se află în ganglionii anexați nervilor VII, IX și X","corneea este transparentă, neavând vase de sânge, dar are în structura sa numeroase fibre nervoase ","2"};
        String[] bio36 = new String[]{"Identificați afirmația corectă: ","pe sclerotică se inseră mușchii intrinseci ai globului ocular ","pata galbenă reprezintă locul de ieșire a nervului optic din globul ocular și de intrare a arterelor globului ocular ","organul activ al acomodării este mușchiul ciliar ","culorile roșu, albastru și galben sunt culori primare sau fundamentale ","conurile sunt mult mai sensibile decât bastonașele","3"};
        String[] bio37 = new String[]{"Identificați afirmația FALSĂ:","prin expunerea mult timp la lumina slabă, pigmentul vizual atât din conuri, cât și din bastonașe este descompus în retinen și opsină ","stimularea egală a celor trei tipuri de conuri provoacă senzația de alb","axonii proveniți din câmpul retinei ( câmpul temporal ) nu se încrucișează și trec în tractul optic de aceeași parte ","peretele lateral al urechii medii este reprezentat de timpan ","între labirintul osos și cel membranos se află perilimfa   ","1"};
        String[] bio38 = new String[]{"Identificați afirmația FALSĂ:","din partea inferioară a saculei pornește canalul cohlear care conține organul Corti ","canalul cohlear conține perilimfă ","fiecare neuron senzitiv din ganglionul spiral Corti transmite impulsuri nervoase de la o anumită zonă a membranei bazilare ","receptorii vestibulari sunt situați în labirintul membranos ","vederea binoculară conferă abilitatea vederii în profunzime ( stereoscopică )","2"};
        String[] bio39 = new String[]{"Identificați afirmația FALSĂ:","unul din defectele vederii cromatice este cunoscut sub denumirea de daltonism ","sensibilitatea receptorilor vizuali este foarte mare ","sensibilitatea celulelor fotoreceptoare este cu atât mai mare, cu cât ele conțin mai mult pigment ","reflexul pupilar fotomotor constă în contracția mușchilor circulari ai irisului, urmată de midriază, ca reacție la stimularea cu lumină puternică a retinei ","la contactul dintre substanțele sapide și celulele receptoare ale mugurelui gustativ se produce o depolarizare a acestora ","4"};
        String[] bio40 = new String[]{"Identificați afirmația corectă:","aria gustativă este situată în partea superioară a girului postcentral ","determinarea sensibilității olfactive se face în laboratoare specializate, cu esteziometrul Weber ","acuitatea olfactivă este invers proporțională cu concentrația substanței odorante ","inervația motorie a fusurilor neuromusculare este asigurată de axonii α din cornul anterior al măduvei ","corpusculii Ruffini sunt sensibili la temperaturi de sub 25⁰C","3"};
        String[] bio41 = new String[]{"Identificați afirmația FALSĂ:","receptorii pentru durere se adaptează puțin sau deloc în prezența stimulului ","terminațiile libere din piele sunt arbirizații dendritice ale neuronilor senzitivi din ganglionii spinali, distribuite printre celulele epidermului ","pielea este sediul receptorilor pentru mai multe tipuri de sensibilități ","în epiderm nu pătrund vase, acesta fiind hrănit prin osmoză din lichidul intercelular ","distanța cea mai mare la care vârfurile unui compas sunt simțite separat dă acuitatea tactilă","5"};
        String[] bio42 = new String[]{"Identificați afirmația FALSĂ: ","acuitatea tactilă este de 50 mm la vârful limbii ","fusurile neuromusculare sunt formate din 5-10 fibre musculare modificate, numite fibre intrafusale, conținute într-o capsulă conjunctivă ","pentru ca o substanță să stimuleze receptorii olfactivi, trebuie să fie volatilă și să aibă o anumită concentrație în aerul respirator","analizatorul gustativ intervine în declanșarea reflexă necondiționată a secreției glandelor digestive","irisul are rolul unei diafragme care permite reglarea cantității de lumină ce sosește la retină ","1"};
        String[] bio43 = new String[]{"Identificați afirmația FALSĂ:","sclerotica, tunica opacă, reprezintă 5/6 din tunica fibroasă ","în partea sa anterioară, coroida este prevăzută cu un orificiu prin care iese nervul optic","conurile sunt adaptate pentru vederea diurnă, colorată, la lumină intensă","camera vitroasă este situată înapoia cristalinului ","la nivelul ariei vizuale primare, cea mai întinsă reprezentare o are macula ","2"};
        String[] bio44 = new String[]{"Identificați afirmația corectă:","tractul optic  conține fibre de la un singur glob ocular ","ciocanul și nicovala au fiecare câte un mușchi","melcul osos este situat posterior de vestibul","otolitele sunt mai dense decât endolimfa ","glaucomul reprezintă prima cauză de pierdere a vederii ","4"};

        String[] fiz1 = new String[]{"Una dintre următoarele afirmaţii NU este corectă:","o suprafaţă care difuzează lumina în toate direcţiile este o suprafaţă mată","reflexia pe o suprafaţă neregulată este o reflexie difuză","dacă suprafaţa de separaţie dintre două medii este netedă, reflexia este regulată","formarea imaginii unui obiect prin reflexie este posibilă doar pentru reflexia difuză","dacă suprafaţa de separaţie dintre două medii este netedă, reflexia este dirijată","4"};
        String[] fiz2 = new String[]{"O suprafaţă este considerată mată dacă:","razele de lumină incidente sunt reflectate în toate direcţiile","razele reflectate sunt paralele între ele","se produce fenomenul de reflexie regulată","razele incidente sunt paralele între ele","se produce fenomenul de reflexie dirijată","1"};
        String[] fiz3 = new String[]{"Una dintre următoarele afirmaţii legate de fenomenul de reflexie şi refracţie NU este corectă:","unghiul de reflexie este egal cu unghiul de incidenţă","raza incidenţă, normala la suprafaţă în punctul de incidenţă şi raza refractată sunt coplanare","raportul dintre sinusul unghiului de incidenţă şi sinusul unghiului de refracţie este constant","raza incidenţă, normala la suprafaţă în punctul de incidenţă şi raza reflectată sunt coplanare","în cazul refracţiei, unghiul de deviaţie este dat de suma dintre unghiul de incidenţă şi cel de refracţie","5"};
        String[] fiz4 = new String[]{"Unghiul dintre direcţia razei incidente şi direcţia razei refractate se numeşte:","unghi de reflexie","unghi de incidenţă","unghi de deviaţie","unghi de refracţie","unghi de difuziune","3"};
        String[] fiz5 = new String[]{"Una dintre următoarele afirmaţii NU este corectă:","legile reflexiei sunt valabile şi pentru reflexia difuză","formarea imaginii unui obiect prin reflexie este posibilă doar pentru reflexia dirijată","razele reflectate de o suprafaţă mată sunt paralele între ele","în cazul corpurilor metalice lustruite, reflexia este dominantă ","culoarea corpurilor se datorează selectivităţii absorbţiei","3"};
        String[] fiz6 = new String[]{"Un fascicul divergent de lumină cade pe o oglindă planăFasciculul reflectat este totdeauna:","divergent","paralel","convergent","paraxial","nicio variantă de răspuns nu este corectă","1"};
        String[] fiz7 = new String[]{"Un fascicul convergent de lumină cade pe o oglindă plană, vârful fasciculului fiind în  spatele oglinziiFasciculul reflectat este totdeauna:","divergent","paralel","convergent o","paraxial","nicio variantă de răspuns nu este corectă","3"};
        String[] fiz8 = new String[]{"Una dintre următoarele afirmaţii referitoare la formarea imaginii obiectelor reale printr-o oglindă plană NU este corectă:","distanţa obiect-oglindă este egală cu distanţa oglindă-imagine","dimensiunea imaginii este egală cu dimensiunea obiectului","imaginea obţinută este reală","imaginea se formează la intersecţia prelungirilor razelor reflectate","imaginea se stabileşte folosind legile reflexiei","3"};
        String[] fiz9 = new String[]{"Prin definiţie, curentul electric reprezintă:","mişcarea ordonată a electronilor","mişcarea ordonată a sarcinilor electrice","diferenţa de potenţial electric","mişcarea dezordonată a sarcinilor electrice libere","lucrul mecanic efectuat de o sarcină electrică","2"};
        String[] fiz10 = new String[]{"Printre efectele produse de trecerea curentului electric printr-un circuit NU se numără următoarele:","efectul termic şi efectul magnetic","efectul magnetic şi efectul chimic","efectul chimic şi efectul termic","efectul termic, efectul magnetic şi efectul chimic","efectul Compton şi efectul Doppler","5"};
        String[] fiz11 = new String[]{"Precizaţi care dintre următoarele afirmaţii pentru mărimea fizică intensitate curent electric NU este corectă (unde: Q - sarcina electrică, Δt - intervalul de timp, E - tensiunea electromotoare, U - diferenţa de potenţial, R - rezistenţa electrică):","reprezintă debitul de sarcină electrică transportată Q","I= Q/ Δt","I= Q/E","unitatea de măsură în SI este Amperul","se poate calcula conform relaţiei I = U/R","3"};
        String[] fiz12 = new String[]{"Unitatea de măsură în SI pentru intensitatea curentului electric este:","V (Volt)","J (Joule)","A(Amper)","Hz (Hertz)","T (Tesla)","3"};
        String[] fiz13 = new String[]{"1 Coulomb este definit ca:","sarcina electrică transportată printr-o secţiune transversală a unui conductor, în               timp deo secundă, de un curent electric continuu, a cărui intensitate este variabilă","sarcina electrică transportată printr-o secţiune transversală a unui conductor, în     timp de o secundă, de un curent electric constant, a cărui intensitate este de 1 Amper","sarcina electrică transportată printr-o secţiune transversală a unui conductor, în timp de o secundă, de un curent electric continuu, a cărui intensitate este de 1 Amper","sarcina electrică transportată printr-o secţiune transversală a unui conductor, într- un timp At = 10 s, de un curent electric constant, a cărui intensitate este de 1 Amper","sarcina electrică transportată printr-o secţiune transversală a unui conductor, în timp de o secundă, de un curent electric continuu, a cărui intensitate este de 1 Volt","2"};
        String[] fiz14 = new String[]{"Bilanţul energetic pentru circuitul simplu electric se poate scrie ca (unde: Wgen - energia furnizată de generator întregului circuit, Wext - energia furnizată de generator circuitului extern, Wint - energia furnizată de generator circuitului interior):","Wgen = Wext  - wint ","Wgen = ΔWint","Wgen = Wext/Wint"," Wgen = Wext  + Wint","Wgen = Wint/Wext","4"};
        String[] fiz15 = new String[]{"în cazul unui circuit electric simplu, care conţine un singur generator, precizaţi care dintre relaţiile următoare NU este corectă pentru tensiunea electromotoare (E)( unde: Wgen - energia furnizată de generator întregului circuit, Wext - energia furnizată de generator circuitului extern, Wint - energia furnizată de generator circuitului interior, Q - sarcina electrică, U- căderea de tensiune pe circuitul exterior, u - căderea de tensiune pe circuitul interior):"," E= Wgen/q","E= (sum)Ek k , k > 1","E=( Wext+Wint)/q","E= U + u ","E= Wext/q + Wint/q","2"};
        String[] fiz16 = new String[]{"Instrumentul de măsură utilizat pentru măsurarea tensiunii electrice se numeşte:","voltmetru","ohm metru","ampermetru","tensiometru","electrometru","1"};
        String[] fiz17 = new String[]{"Pentru a măsura tensiunea electrică de la bornele unui rezistor, voltmetrul trebuie să fie conectat:","în serie cu acesta","nu are importanţă, atât timp cât este conectat","în serie, apoi în paralel cu acesta","în paralel cu acesta"," în paralel şi în serie cu acesta","4"};
        String[] fiz18 = new String[]{"Volumul molar depinde de natura substanţei şi foarte puţin de presiune şi temperatură în cazul:","lichidelor şi gazelor","lichidelor şi solidelor","gazelor şi solidelor","gazelor","lichidelor, solidelor şi gazelor","2"};
        String[] fiz19 = new String[]{"Volumul molar depinde de presiune şi temperatură, dar şi foarte puţin de natura substanţei în cazul:","lichidelor","lichidelor şi gazelor","solidelor","lichidelor, solidelor şi gazelor","gazelor","5"};
        String[] fiz20 = new String[]{"Numărul lui Avogadro (Na) este o mărime fizică derivată în SI (unde: N - numărul de entităţi elementare din sistem, n - numărul volumic, v - cantitatea de substanţă din sistem), definită prin relaţia:","NA = N / n","NA = v / n","NA = N / v","NA = v x N","NA = n x N","3"};
        String[] fiz21 = new String[]{"Numărul lui Avogadro (Na):","se măsoară în mol-1","se măsoară în mol","se măsoară în kmol","se măsoară în 102 mol","este o mărime adimensională","1"};
        String[] fiz22 = new String[]{"Numărul de molecule conţinute într-un volum V de hidrogen aflat în condiţii normale de presiune şi temperatură este dat de relaţia (unde: Na - numărul lui Avogadro, Vµ0 -volumul molar în condiţii normale de presiune şi temperatură):","N= NA / Vµ0","N = Vx NA / V","N = NA / V","N = V x NA","N = Vµ0  x  NA / Vµ0","2"};
        String[] fiz23 = new String[]{"Numărul de molecule conţinute într-un volum V= 22,4 m3 de oxigen aflat în condiții normale de presiune și temperatură este:","6,022* 1025","6,022","6,022* 1027","6,022* 1026","2* 6,022 * 1026","4"};
        String[] fiz24 = new String[]{"Volumul molar are valoarea 22,4 m3/Kmol în următoarele condiţii:","la temperatură şi presiune atmosferică normală","indiferent de condiţiile în care se află gazul","la presiune atmosferică normală şi indiferent de temperatură","la 0 °C şi indiferent de presiune","la 273,15K şi indiferent de presiune","1"};
        String[] fiz25 = new String[]{"Numărul de molecule cuprinse într-o masă m de hidrogen aflat în condiţii normale de presiune şi temperatură este dat de relaţia (unde: Na - numărul lui Avogadro, Vµ0 - volumul molar în condiţii normale de presiune şi temperatură, µ - masa molară, n - numărul volumic.):","N= m* Na / µH2","N= NA * Vµ0 / V","N= m* NA / n","N= n* Na / m ","N= Vµ0 * NA / V ","1"};
        String[] fiz26 = new String[]{"Numărul de molecule cuprinse într-o masă m = 32 kg de oxigen aflat în condiţii normale de presiune şi temperatură este:","6,022*1025","2*6,022*1026","6,022*1027","6,022*1026","2*6,022*1025","4"};
        String[] fiz27 = new String[]{"1Densitatea substanţelor aflate în stare solidă sau lichidă poate fi scrisă sub forma relaţiei (unde: p - masa molară, Vµ - volumul molar, Vµ0 - volumul molar în condiţii normale de presiune şi temperatură, Na - numărul lui Avogadro):","Ρ= µ / V µ","Ρ = Vµ / µ"," Ρ= µ / NA","Ρ= NA * µ/ Vµ0","Ρ= NA * µ / Vµ","1"};
        String[] fiz28 = new String[]{"Numărul volumic:","se măsoară în m3","se măsoară în m -3","se măsoară în m -2","se măsoară în m2","este o mărime adimensională","2"};
        String[] fiz29 = new String[]{"NU este un sistem termodinamic:","apa dintr-un vas","un organism viu","un atom (de H2)","aerul dintr-o încăpere","gazul dintr-un balon umplut cu He","3"};
        String[] fiz30 = new String[]{"Parametrii de stare pentru un sistem termodinamic simt:","parametri intensivi şi extensivi","doar parametri intensivi","doar parametri extensivi","doar parametri microscopici","doar parametri macroscopici","1"};
        String[] fiz31 = new String[]{"Un parametru intensiv al unui sistem termodinamic este:","masa","volumul","energia internă","presiunea","cantitatea de substanţă","4"};
        String[] fiz32 = new String[]{"Un parametru intensiv al unui sistem termodinamic este:","volumul","temperatura","energia internă","masa","cantitatea de substanţă","2"};
        String[] fiz33 = new String[]{"Un parametru intensiv al unui sistem termodinamic este:","densitatea","volumul","energia internă","masa","cantitatea de substanţă","1"};
        String[] fiz34 = new String[]{"1NU este un parametru extensiv:","energia internă","volumul","masa","cantitatea de substanţă","temperatura","5"};
        String[] fiz35 = new String[]{"NU este un parametru extensiv:","masa","presiunea","energia internă","volumul","cantitatea de substanţă","2"};
        String[] fiz36 = new String[]{"NU este un parametru extensiv:","energia internă","volumul","masa","densitatea","cantitatea de substanţă","4"};
        String[] fiz37 = new String[]{"Starea unui sistem termodinamic este staţionară dacă:","parametrii de stare sunt constanţi în timp","parametrii de stare nu sunt constanţi în timp","parametrii de stare sunt constanţi în spaţiu","parametrii de stare nu sunt constanţi în spaţiu","parametrii de stare nu simt constanţi în timp şi spaţiu","1"};
        String[] fiz38 = new String[]{"Un sistem termodinamic izolat evoluează spontan şi ireversibil spre o stare:","de neechilibru termodinamic","nestaţionară","de echilibru termodinamic","experimentală","termodinamică","3"};
        String[] fiz39 = new String[]{"Un proces termodinamic este reversibil dacă îndeplineşte condiţiile:","procesul se desfăşoară într-un singur sens","procesul se poate desfăşura în ambele sensuri şi prin aceleaşi stări intermediare","procesul se desfăşoară prin aceleaşi stări intermediare","procesul se poate desfăşura în ambele sensuri","procesul se poate desfăşura în ambele sensuri, prin diferite stări intermediare","2"};
        String[] fiz40 = new String[]{"Un proces termodinamic este ciclic dacă:","starea finală coincide cu starea iniţială","starea finală nu coincide cu starea iniţială","procesul este cvasistatic","procesul este necvasistatic","procesul este ireversibil","1"};
        String[] fiz41 = new String[]{"Un proces termodinamic NU este ciclic dacă:","starea finală coincide cu starea iniţială","starea finală nu coincide cu starea iniţială","procesul este cvasistatic","procesul este reversibil","procesul este ireversibil","2"};
        String[] fiz42 = new String[]{"Un proces termodinamic NU este ireversibil dacă îndeplineşte condiţiile:","procesul se desfăşoară într-un singur sens","procesul se desfăşoară prin diferite stări intermediare","procesul se poate desfăşura în ambele sensuri","procesul se poate desfăşura în ambele sensuri şi prin aceleaşi stări intermediare","procesul se poate desfăşura în ambele sensuri şi prin diferite stări intermediare","4"};
        String[] fiz43 = new String[]{"Pe un taler de masă M, legat de un resort elastic cu constanta de elasticitate k, punem un corp de masă mîn acest proces de intindere a resortului:","transformarea este reversibilă","transformarea este ireversibilă","transformarea este cvasistatică","transformarea este ciclică","toate răspunsurile sunt incorecte","2"};
        String[] fiz44 = new String[]{"1Parametrul de stare de tip intensiv ce are aceeaşi valoare pentru toate stările sistemelor termodinamice aflate în relaţie de echilibru termic este:","temperatura","presiunea","densitatea","masa","energia internă","1"};
        String[] fiz45 = new String[]{"Principiul zero al termodinamicii se referă la următoarea mărime fizică:","lucru mecanic","energie internă","temperatura empirică","presiune","un parametru extensiv","3"};
        String[] fiz46 = new String[]{"Relaţia de transformare a temperaturii din grade Celsius (t) în grade Kelvin (7) este:","T = t + 273 K","T=t+273 °C","T = t + 273,15 K","T = t+ 100 °C","T = 273 K","3"};
        String[] fiz47 = new String[]{"Relaţia corectă (unde: ΔT- variaţia temperaturii în K, Δt - variaţia temperaturii în °C) este :","ΔT=Δt + 273,15 °C","ΔT= Δt + 273 °C","ΔT= Δt + 273,15 K","ΔT= Δt - 273 K","ΔT= Δt","5"};
        String[] fiz48 = new String[]{"Un interval de temperatură de 1 °C corespunde unui interval de temperatură de:","1K","100K","273 K","273,15 K"," 0 K","1"};
        String[] fiz49 = new String[]{"Temperaturile de referinţă alese în scara Celsius sunt:","1 °C şi 100 °C","0 °C şi 100 °C","0 °C şi 273 °C","1°C şi 273,15 K","32 F şi 212 F","2"};

        String[] chim1 = new String[]{"Alegeţi afirmaţia corectă:","Atomul de azot hibridizat sp3 are 3 orbitali hibrizi sp3, din care 2 monoelectronici;","Atomul de carbon hibridizat sp3 are 4 orbitali hibrizi sp3 monoelectronici, cu care realizează 4 1egături σ","Atomul de oxigen hibridizat sp2 are 4 orbitali hibrizi sp3, dintre care 2 monoelectronici cu care realizează 2 legături σ","Orbitalii hibrizi sp3 au nivele diferite de energie;","Atomul de N nu poate avea hibridizare sp3.","2"};
        String[] chim2 = new String[]{"Alchena C7H14 conţine un atom de carbon cuaternar, 2 atomi de carbon terţiari şi 4 atomi de carbon primari. Formula alchenei este:","3,3-dimetil-1 -pentenă;","3-metil-2-hexenă;","3,4-dimetil-2-pentenă;","2-metil-2-pentenă;","4,4-dimetil-1 -pentenă.","3"};
        String[] chim3 = new String[]{"Alegeţi afirmaţia corectă:","Lungimea unei legături covalente scade în ordinea: triplă, dublă, simplă;","Legăturile Csp3-H sunt puternic polare;","In structura propadienei sunt 3 legături σ şi o legătură π;","Atomul de carbon poate realiza în compuşii organici numai legături simple;","Moleculele cu o structură perfect simetrică sunt molecule nepolare","5"};
        String[] chim4 = new String[]{"În structura acroleinei avem:","Un carbon primar, unul secundar şi unul terţiar;","Un carbon primar, unul nular şi unul terţiar;","Un carbon secundar, unul cuaternar şi unul terţiar;","Doi atomi de carbon primari şi unul terţiar;","Doi atomi de carbon.terţiari.","1"};
        String[] chim5 = new String[]{"Pristan este denumirea unul alean prezent în uleiul obţinut din ficatul de rechin, denumirea sa după IUPAC fiind 2,6,10,14-tetrametilpentadecan. Care afirmaţie referitoare la acest compus este adevărată ?","Este un alean cu formula moleculară C19H40 şi 6 atomi de carbon primari;","Este un cicloalcan cu formula moleculară C19H40 şi 4 atomi de carbon primari;","Este un alean cu formula moleculară C20H42 şi 5 atomi de carbon primari;","Este un alean cu formula moleculară C19H40 şi 4 atomi de carbon primari;","Este un alean cu formula moleculară C19H40 şi 2 atomi de carbon primari.","1"};
        String[] chim6 = new String[]{"Se dau compuşii: acetilură de sodiu (1), propionat de amoniu (2), etoxid de sodiu (3), iodură de tetrametil-amoniu (4). Care dintre aceştia prezintă legături ionice ?","1 şi 2;","2 şi 3;","3 şi 4;","1 şi 4;","toţi","5"};
        String[] chim7 = new String[]{"Radicalii monovalenţi ai alcanilor cu patru atomi de carbon sunt în număr de:","unu;","doi;","trei;","patru;","cinci.","4"};
        String[] chim8 = new String[]{"Pentru următoarea structură chimică, masa moleculară este:","347","348","349          ","350      ","333","3"};
        String[] chim9 = new String[]{"O hidrocarbură conţine 83,33% C şi are masa molară egală cu 72. Care este formula ei moleculară?","C4HsO;","C5H12;","C6Hi2;","C4H10;"," C5Hg.","2"};
        String[] chim10 = new String[]{"Zaharoza, Ci2H22On are compoziţia masică procentuală ( % H = 6,43% , % H = 6,48%, % H = 6,48% , % H = 6,48%, % O = 51,46; % O = 41,41; % O= 61,41; % O = 31,41;):","%C = 42,11%","%C = 52,11%  ","% C = 32,11%","%C = 62,11%","Niciun răspuns corect.","1"};
        String[] chim11 = new String[]{"Care este formula moleculară a substanţei cu compoziţia 31,9% C; 5,3% H şi 62,89% CI, ce are densitatea faţă de aer egală cu 3,9?","C3H7CI;","C3H6C12;","C3H4C12;","C2H4C12;","Niciun răspuns corect.","2"};
        String[] chim12 = new String[]{"3Radicalul divalent al metanului se numeşte:","metil;","metin;","metilen;","metanoat;","metanii","3"};
        String[] chim13 = new String[]{"Denumirea corectă a izoaleanului de mai jos, este:","2-izopropil-3-metil-heptan;","4-etil-2,2-dimetil-hexan;","3-etil-5,5-dimetil-hexan;","4-etil-2,2-dimetil-heptan;","3-etil-2,2-dimetil-hexan.","2"};
        String[] chim14 = new String[]{"Novocaina, esteml acidului p-amino-benzoic cu N,N-dietilaminoetanolul conţi","7 atomi de C primari, 5 atomi de carbon terţiari;","4 atomi de C primari, 5 atomi de carbon terţiari;","4 atomi de C primari, 4 atomi de carbon terţiari;","2 atomi de C primari, 4atomi de carbon terţiari;","Niciun răspuns corect","1"};
        String[] chim15 = new String[]{"Formulei moleculare C4H8 îi corespund:","trei alchene;","2 cicloalcani;","2 izomeri geometrici;","4 alchene, 2 cicloalcani;","2 izomeri de catenă .","4"};
        String[] chim16 = new String[]{"Se dă schema de mai jos | Compusul A este:","clorură de izopropil;","clorură de sec-butil;","clorură de butil;","clorură de sec-pentil;","clorură de terţ-butil şi clorură de izobutil.","5"};
        String[] chim17 = new String[]{"Câţi izomeri ciclici prezintă hidrocarbura a cărei masă moleculară este egală cu 56:","doi;","trei;","patru;","unu;","cinci.","1"};
        String[] chim18 = new String[]{"Care dintre următoarele alchene nu conţin atomi de carbon în două stări de hibridizare:","1 - butenă;","propenă;","etenă;","2 - butenă;","izobutenă.","3"};
        String[] chim19 = new String[]{"O hidrocarbură saturată cu formula C4H8 conţine numai unsingur atom de carbon primarStructura ei este:","1 - butenă;","2 - butenă;","               metilciclopropan;","2 - metil - propenă;","ciclobutan.","3"};
        String[] chim20 = new String[]{"Care este numărul minim de atomi de carbon ai unui alean, pentru ca în urma cracării să rezulte şi butenă?","7;","5;","             6;","8;","4.","2"};
        String[] chim21 = new String[]{"Care este hidrocarbura ce reacţionează cu o soluţie de [Cu(NH3)2]OH şi conţine n atomi de carbon şi 3n-5 atomi de hidrogen?","1 -butenă;","propină;","1 - hexină;","               2 - metil -1 - butenă;","nu există o hidrocarbură cu această formulă.","2"};
        String[] chim22 = new String[]{"Alegeţi afirmaţia corectă:","Conformaţia se referă la aranjamentul spaţial al moleculei organice rezultat al rotaţiei atomilor de carbon (cu substituenţii lor) în jurul axei simple C-C care-i uneşte ;","Conformaţia se referă la aranjamentul spaţial al moleculei organice rezultat al rotaţiei atomilor de carbon (cu substituenţii lor) în jurul axei C=C care-i uneşte;","Conformaţia se referă la aranjamentul plan al moleculei organice rezultat al rotaţiei atomilor de carbon (cu substituenţii lor) în jurul axei care-i uneşte;","               niciun răspuns exact;","în moleculele cu mai multe legături simple C-C, numărul conformaţiilor posibile scade","1"};
        String[] chim23 = new String[]{"În clasa alcanilor nu se întîlneşte reacţia de:","substituţie;","oxidare;","izomerizare;","adiţie;","alt răspuns.","4"};
        String[] chim24 = new String[]{"Pentru obţinerea de alchene din derivaţi monohalogenaţi, se foloseşte:"," acid sulfuric diluat;","hidroxid de potasiu alcoolic;","               soluţie de KOH (la cald);","hidroxid de sodiu","carbonat de sodiu.","2"};
        String[] chim25 = new String[]{"Prin hidroliza compusului de adiţie a acidului sulfuric la 2-butenă rezultă:","3-butenol-l ","2-butanal;","               2-butanol;","acid butanoic","2,3-butandiol.","3"};
        String[] chim26 = new String[]{"Într-un cilindru de 10 L se găsesc 92,8 g alean la presiunea de 3,84 atm şi temperatura de 20°C. Denumirea aleanului din cilindru este:","metan;","propan;","butan;","               pentan;","niciun răspuns corect.","3"};
        String[] chim27 = new String[]{"De câte ori se obţine mai mult dioxid de carbon prin arderea a 10 m3 de propan, comparativ cu arderea a 10 m3 de metan?","de două ori;","de trei ori;","de patru ori;","de cinci ori;","de zece ori.","2"};
        String[] chim28 = new String[]{"Care din afirmaţii este corectă referitor la alcadiene:","conţin mai multe duble legături;","conţin patru atomi de H mai puţin decît alcanii cu acelaşi număr de atomi de carbon;","simetria orbitalilor de legătură este digonală;","au formula generală CnH2n ;","sunt izomeri de funcţiune cu cicloalcanii.","2"};
        String[] chim29 = new String[]{"Prin adiţia bromului la 1,3 butadienă, în raport echimolecular, se obţine în cantitate mai mare:","1,4-dibrom-2-butenă;","1,3-dibrom-2-butenă;","2,3-dibrom-1 -butenă;","3,4-dibrom-1 -butenă;","               2,3-dibrom-butan.","1"};
        String[] chim30 = new String[]{"Izomerii cu structură liniară pentru hidrocarbura cu formula C4H6 sunt în număr de:","2;","3;","4;","5;","1.","3"};
        String[] chim31 = new String[]{"Câte structuri monociclice se pot scrie pentru hidrocarbura cu formula C4H6:","3;","5;","7;","4;","2 ","4"};
        String[] chim32 = new String[]{"Care din următorele reacţii Friedel-Crafts nu poate avea loc?","clorură de benzil + clorură de butanoil;","izopropilbenzen + clorură de etil;","monoclorbenzen + clorură de vinil;","benzen + clorură de alil;"," benzen + clorură de benzoil.","3"};
        String[] chim33 = new String[]{"Care este raportul molar 2-butenă: K2CR2O7: H2S04 la oxidarea 2-butenei, considerând reacţia stoechiometrică?","3:4:16;","1:4:8 ;","2:2:4 ;","1:2:8 ;","3:3:8.","1"};
        String[] chim34 = new String[]{"Care sunt hidrocarburile C6H10 care nu reacţionează cu [Cu(NH3)2]OH ?","1 -hexină şi 2-hexină;","2-hexină şi 3-metil-1 -pentină;","2-pentină şi 2-butină;","2-hexină şi 4-metil-2-pentină;","3-hexină şi 4-etil-l-butină.","4"};
        String[] chim35 = new String[]{"Care din următoarele afirmaţii este incorectă referitor la naftalină:","are un caracter aromatic mai puţin pronunţat decât benzenul;","poziţiile a şi p sunt la fel de reactive;","prin sulfonare la 180°C se obţine acidul β-naftalin-sulfonic;","prin nitrare directă se obţine α-nitro-naftalină;","se oxidează mai uşor decît benzenul.","2"};
        String[] chim36 = new String[]{"Naftalina este:","o hidrocarbură aromatică cu nuclee condensate liniar;","o hidrocarbură aromatică cu nuclee condensate angular;","o hidrocarbură alifatică cu nuclee condensate liniar;","un solvent nepolar;","o hidrocarbura care se poate oxida uşor cu KMnO4","1"};
        String[] chim37 = new String[]{" Cantitatea de produs, exprimată în grame şi în moli, rezultată în urma reacţiei de clorurare a unui mol de orto-xilen, la lumină, realizată cu un randament de 80%, este:","1 mol=244 g;","0,80 moli=195,2 g;","1 mol=173 g;","0,80 moli=138,4 g;","2 moli=346 g.","2"};
        String[] chim38 = new String[]{"în care din următoarele reacţii se obţin produşi cfe nuprezintă izomerie geometrică:"," oxidarea benzenului cu O2 din aer la 500°C şi V2O5 ;","adiţia unui mol de clor la 2-butină în solvent inert;","adiţia bromului la C2H2 în raport molar 1:1;","adiţia hidrogenului la 2-butină în prezenţa Pd otrăvit cu săruri de Pb;","deshidratarea 2-butanolului în prezenţă de H2S04 la cald.","1"};
        String[] chim39 = new String[]{"Tetralina şi ciclohexil-benzenul sunt:","identici;","omologi;","izomeri de poziţie;","izomeri de catenă;","în niciun fel de relaţie.","5"};
        String[] chim40 = new String[]{"Care din hidrocarburile de mai jos va forma acid benzoic la oxidare cu K2CR2O7/H2S04 ?","2-fenil-l-butena;","a-metil-stirenul;","3-fenil-propena;","1,2-difenil-etena;","2-fenil-2-butena.","4"};
        String[] chim41 = new String[]{"Prin hidroliza a 457 kg carbură de calciu tehnică cu exces de apă, s-au obţinut 112 m3 (c.n.) acetilenă. Puritatea carburii de calciu a fost:","66,66%;","70,02%;","32%;","35%;","altă valoare.","2"};
        String[] chim42 = new String[]{"Raportul dintre masa moleculară a unei alchene şi a unei alchine este 7:10, iar raportul dintre numărul total al atomilor este 6:7. Cele două hidrocarburi sunt:","etena si propina;","propena şi acetilena;","butena şi pentina;","butena şi propina;","etena şi butina","1"};
        String[] chim43 = new String[]{"Ce catalizator se foloseşte în reacţia de hidratare a acetilenei (reacţia Kucerov)?","HgS04şiH2S04;","CuCl şi NH4C1;","(CH3COO)2Zn;","[Ag(NH3)2]OH;","HgCl2.","1"};
        String[] chim44 = new String[]{"Ce masă de acid clorhidric reacţionează, în raport molar 1 : 1, cu 1680 m3  de acetilenă de puritate 80%, dacă randamentul reacţiei este 75%?","1290 kg;","3109 kg;","1642,5 kg;","2190 kg;","2190L.","3"};
        String[] chim45 = new String[]{"Alchenele se mai numesc olefine pentru că:","derivaţii lor cloruraţi sunt insolubili în apă;","derivaţii lor cloruraţi sunt solubili în apă;","sunt lichide uleioase;","derivaţii lor clomraţi şi bromuraţi sunt lichide uleioase;","nici un răspuns exact.","4"};
        String[] chim46 = new String[]{"Formula generală a dienelor este:","CnH2n+2;","CnH 3n+2;","C2nHn-2;","CnH2a-2;"," CnH3n-2","4"};
        String[] chim47 = new String[]{"O alchenă cu formula moleculară C7H34 prin hidrogenare formează n- heptanul, iar prin oxidare cu K2CR207/H+ conduce la doi acizi monocarboxilici omologi. Care este alchena ?","2-metil-3-hexena;","3-hexena;","2-heptena;","3-heptena;","4-heptena.","4"};
        String[] chim48 = new String[]{"Două alchene cu formula moleculară C5H10 folosesc la oxidare cu K2CR207/H2S04 aceeaşi cantitate de agent oxidant. Ele sunt:","1-pentenă şi 2-pentenă;","1-pentenă şi 3-metil-1-pentenă;","2-metil-1 –butenă şi 1-pentenă;","1-pentenăşi 3-metil-l-butenă;","2-metil-1 –butenă şi 2-metil-2-butenă.","4"};
        String[] chim49 = new String[]{"Care din derivaţii halogenaţi de mai jos nu pot participa la o reacţie Friedel- Crafts?","clorură de ciclopentil, clorură de etil;","clorură de propil, clorură de izopropil;","clorură de t-butil, clorură de izobutil;","clorură de benzii, clorură de alil;","o-clor-toluen, 1-clor-propena.","5"};
        String[] chim50 = new String[]{"Care dintre compuşii ce corespund formulei moleculare C9H12, vor da la oxidare şi acid benzoic?","3-fenil-propena;","o-metil-etil-benzenul;","toluenul;","n-propil-benzenul;","1-fenil-propena.","5"};
        String[] chim51 = new String[]{"Un amestec format din 3 moli etan şi 1 mol etenă reacţionează cu un volum de apă de brom 1 molar de:"," 1 L;","2000 mL;","300 mL;","0,004 m3;","5 L .","1"};
        String[] chim52 = new String[]{"O  alchenă cu formula moleculară C6H12 se oxidează cu K2CR207/H+.Raportul masic C02:[0] este 11:16. Care este alchena cu cei mai mulţi atomi de carbon primari care îndeplineşte condiţiile de mai sus?","2-metil-1-pentenă;","2,4-dimetil-1 -pentenă;","2,3-dimetil-l -butenă;","3,3-dimetil-l-butenă;","3-metil- 1-pentenă.","3"};
        String[] chim53 = new String[]{"Care este aleanul, cu formula moleculară C6H14, ce formează prin dehidrogenare două alchene?","2,2,4-trimetil-pentanul;","2-metil-pentanul;","3-metil-pentanul;","2,2-dimetil-butanul;","2,3-dimetil-butanul.","5"};
        String[] chim54 = new String[]{"Cu ce alchenă trebuie alchilat benzenul pentru a obţine o hidrocarbură cu raportul masic C:H=9:1 ?","etenă;","butenă;","propenă;","pentenă;","hexenă..","3"};
        String[] chim55 = new String[]{"O cantitate de 6,4 g naftalina reacţionează complet cu un amestec nitrant care conţine 20% HN03 (procente de masă), obţinându-se un dinitroderivatSoluţia reziduală, după îndepărtarea produsului organic, mai conţine 2% HN03. Masa amestecului nitrant necesară reacţiei este:","31,5 g;","34,5 g;","30,5 g;","3,45 g;","3,25 g.","2"};
        String[] chim56 = new String[]{"La monosulfonarea etilbenzenului s-a folosit o soluţie concentrată de H2S04, c=96%, în exces şi au rezultat 5 moli de derivat sulfonic aromatic. Ştiind că randamentul reacţiei este 60%, masa etilbenzenului luat în lucru este:","530 g C6H5-CH2-CH3;","106 g C6H5-CH2-CH3J","5 moli C6H5-CH2-CH3;","9 moli C6H5-CH2-CH3;","883,3(3) g C6H5-CH2-CH3.","5"};
        String[] chim57 = new String[]{"Procentul masic de hidrogen dintr-un amestec format dintr-un mol de etilbenzen şi 2 moli de stiren este:","8,28% H;","0,82% H;","8,18% H;","0,81% H;","altă valoare.","1"};
        String[] chim58 = new String[]{"La hidrogenarea a 0,2 moli benzen cu 0,1 moli hidrogen, la presiune şi temperatură, se obţine după consumarea hidrogenului:","Ciclohexena;","Ciclohexan;","Ciclohexadiena;","Amestec de ciclohexena şi benzen;","Amestec de ciclohexan şi benzen.","5"};
        String[] chim59 = new String[]{"Amestecul echimolecular a două hidrocarburi aromatice omoloage formează prin ardere 252 g de apă. Care sunt formulele moleculare ale hidrocarburilor, cunoscând că raportul numeric dintre atomii de hidrogen din hidrocarburile respective este 3:4?","C7H8; CgHio;","CgHio; CşHjî ;","C6H6;C7H8;","C7Hg; CgHg;","niciun răspuns nu este corect.","3"};
        String[] chim60 = new String[]{" m-Metil-acetofenona se poate obţine din benzen, prin :","metilare şi apoi acetilare în prezenţa A1C13 anhidră;","acetilare şi apoi metilare în prezenţa A1C13 anhidră;","tratare cu etanal şi apoi metilare;","tratare cu acid propionic şi apoi metilare în prezenţaAlCl3 anhidră;","tratare cu propanonă şi apoi alchilare .","2"};
        String[] chim61 = new String[]{"Dacă benzenul şi naftalina se oxidează la temperatură şi în prezenţa  V2O5 cu cantitatea stoechiometrică de aer, care va fi raportul între cantităţile de oxigen consumate în cele două reacţii, ştiind că hidrocarburile se află în raport molar de 1:2?","2,0;","0,75 ;","2,5 ;","0,5 ;","1,5 .","4"};
        String[] chim62 = new String[]{"Care este alchena cu formula moleculară C6H12 care pentru oxidarea a 0,3 moli consumă 0,1 L soluţie K2CR207 2M (în prezenţa H2S04) ?","2-hexenă;","3-hexenă;","2,3-dimetil-2-butenă;","2-metil-2-pentenă;","3,3-dimetil-l -butenă.","3"};
        String[] chim63 = new String[]{"Ce raport molar va exista între metan şi apă după realizarea conversiei cu un randament de 60%, dacă reactanţii s-au luat iniţial în raport molar CH4:H20=1:3?","1:2 ;","1:3 ;","1:4;","1:5 ;","1:6.","5"};
        String[] chim64 = new String[]{"Prin adiţia apei, în prezenţa HgSO4 şi H2SO4, la acidul butindioic se formează:","acid hidroxi-succinic;","acid dihidroxi-succinic;","acid ceto-succinic;","acid diceto-succinic;","niciun răspuns exact.","3"};
        String[] chim65 = new String[]{"La oxidarea unei alcadiene rezultă acid acetic, acid piruvic şi acid propionicAlcadiena este:","3 -metil-2,4-hexadiena;","3,5-dimetil-2,4-hexadiena;","2,4-heptadiena;"," 3-metil-2,4-heptadiena;","3-metil-2,4-heptadienă sau 4-metil-2,4-heptadiena.","5"};
        String[] chim66 = new String[]{"Referitor la oxidarea catalitică a a-nitro-naftalinei, care afirmaţie este corectă?","se distruge nucleul substituit deoarece gruparea nitro activează nucleul pe care este grefată;","se distruge nucleul substituitdeoarece gruparea nitro dezactivează nucleul pe care este grefată;","se distruge nucleulnesubstituit deoarece gruparea nitro dezactivează nucleul pe care este grefată;","se distrage nucleul nesubstituit deoarece gruparea nitro activează nucleul pe care este grefată;","se distruge oricare din cele două nuclee.","3"};
        String[] chim67 = new String[]{"Alcoolul alilic se poate transforma direct în glicerină prin oxidare cu:","apă de brom;","K2CR2O7/H ;","KMnO4/H+;","[Ag(NH3)2]OH;","KMnO4 în soluţie slab bazică sau neutră.","5"};
        String[] chim68 = new String[]{"Care din izomerii cu formula moleculară C8HI0 va da prin substituţie la nucleu un singur compus monoclorurat?","m-xilenul;","o-xilenul;","p-xilenul;","etil-benzenul;","vinil-benzenul;","3"};
        String[] chim69 = new String[]{"Care din amestecurile de mai jos consumă la combustie un volum mai mare de aer?","un mol acetilenă şi doi moli metan;","doi moli etenă şi un mol acetilenă;","doi moli etan şi un mol acetilenă;","doi moli acetilenă şi un mol metan;","doi moli acetilenă şi doi moli etenă;","5"};
        String[] chim70 = new String[]{"O hidrocarbură cu formula generală CnH2n.6 dă la nitrare un singur mononitroderivat ce conţine 9,27% azot.Hidrocarbura este:","etilbenzenul;","stirenul;","mesitilenul;","o-xilenul;","p-xilenul;","5"};
        String[] chim71 = new String[]{"reactivitatea la oxidare şi la hidrogenare variază în acelaşi sens în seria benzen, naftalină şi antracen.","1,3,4;","1,2,3 ;","2,3,4;","1,4;","toate.","5"};
        String[] chim72 = new String[]{"La cracarea propanului se obţine un amestec de gaze ce conţine 20% propenă, 10% etenă şi propan nereacţionat. Ce volum de etenă (c.n.) se obţine din 500m3 propan ?","71.42L;","7142mL;","166,6m3;","71,42m3;","500m3.","4"};
        String[] chim73 = new String[]{"Gruparea nitro este un substituent de ordinul II; aceasta poate fi transformată intr-un substituent de ordinul I prin tratare cu:","clorură de benzoil;","hidrogen;","anhidridă acetică;","clor în prezenţa A1C13 anh.;","niciun răspuns exact.","2"};
        String[] chim74 = new String[]{"Alegeţi afirmaţia corectă :","Hidrocarburile aromatice au NE cel puţin 4 ;","Hidrocarburile aromatice au NE cel puţin 3 ;","Benzofenona are NE egală cu 8 ;","Hidrocarburile saturate ciclice au NE egală cu 0 ;","Alcanii si izoalcanii cu acelaşi număr de atomi de carbon nu au aceeaşi NE","1"};
        String[] chim75 = new String[]{"Mercaptanii sunt compuşi organici cu sulf Sunt corecte afirmaţiile :","1,2,5","1,3,4","4,5;","1,2,4,5","Toate afirmaţiile","3"};
        String[] chim76 = new String[]{"La oxidarea n-propil benzenului cu KMnCL in mediu acid, se consuma pentru 1 mol:","5 [O];","4 [O];","7 [O];","8 [O];","1 [O].","1"};
        String[] chim77 = new String[]{"Radicalii ce derivă de la benzen se numesc:","benzii;","benziliden;","fenil;","fenil, fenilen;","niciun răspuns exact","3"};
        String[] chim78 = new String[]{"Prin oxidarea naftalinei cu KMnO4 în mediu neutra rezultă:","acid benzoic;","acid ftalic;","naftochinonă;","dioxid de carbon şi apă;","reacţia nu are loc.","5"};
        String[] chim79 = new String[]{"Care este randamentul reacţiei de transformare a metanului în acetilenă, dacă se folosesc 67,2 m3 CH4 şi se obţin 22,4 m3 acetilenă?","33,3%;","50%;","66,7%;","83,4%;","niciun răspuns exact.","3"};
        String[] chim80 = new String[]{"Un amestec metan, etenă şi acetilenă în raport molar 1:1:1 are un conţinut procentual de carbon de:","79,5%;","92%;","67,16%;","85,71%;","niciun răspuns exact.","4"};
        String[] chim81 = new String[]{"O cantitate de 0,3 moli de 2-metil-2-butenă se oxidează în mediu de H2SO4 cu o soluţie de K2CR2O7 0,2 M al cărui volum este de (cm3):","1000;","2000;","1500;","3000;","2500.","3"};
        String[] chim82 = new String[]{"Se ard complet 112 L amestec metan şi propan. La spălarea gazelor uscate cu o soluţie de NaOH volumul lor scade cu 201,6 LConcentraţia metanului din amestec (în procente de volum) este:","60%;","30%;","65%;","40%;","niciun răspuns exact.","1"};
        String[] chim83 = new String[]{"Un amestec echimolecular de hexenă şi pentenă are compoziţia procentuală (masă):","45,45%, 54,54%;","54,54%, 45,45%;","50 % , 50%;","30,5%, 69,5%;","69,5%, 30,5%.","2"};
        String[] chim84 = new String[]{"Să se identifice hidrocarburile 1, 2, 3 cu formula moleculară C5Hg dacă 1 formează un precipitat prin tratare cu hidroxid diaminoargentic, 1 şi 2 prin hidrogenare formează n-pentanul, iar 3 prin hidrogenam totală formează C5H10:","1 -pentină, 2-pentină, 3-pentină;","2-pentină, 1-pentină, ciclopentenă","1-pentină, 2-pentină, ciclopentenă;","3-metil-l-pentină, 2-pentină, ciclohexenă;","1-pentină, 2-metil-2-pentină, pentadienă.","3"};
        String[] chim85 = new String[]{"Care este compoziţia în procente de volum al unui amestec gazos echimolecular de alcani - alchene - alchine?","depinde de masele moleculare ale hidrocarburilor;","depinde de raportul molar;","33,33% , 33,33% , 33,33%;","40% , 30%, 30%;","niciun răspuns exact.","3"};
        String[] chim86 = new String[]{"Prin izomerizarea a 29 g n-butan rezultă un amestec de hidrocarburi care conţine 4,8 g carbon terţiar. Procentul de n-butan din amestecul rezultat este:","10%;","15% ;","20%;","25%;","30%.","3"};

        String[][] biologie = new String[][] { bio1, bio2, bio3, bio4, bio5, bio6, bio7, bio8, bio9, bio10, bio11, bio12, bio13, bio14, bio15, bio16, bio17, bio18, bio19, bio20, bio21, bio22, bio23, bio24, bio25, bio26, bio27, bio28, bio29, bio30, bio31, bio32, bio33, bio34, bio35, bio36, bio37, bio38, bio39, bio40, bio41, bio42, bio43, bio44};
        String[][] chimie = new String[][] { chim1, chim2, chim3, chim4, chim5, chim6, chim7, chim8, chim9, chim10, chim11, chim12, chim13, chim14, chim15, chim16, chim17, chim18, chim19, chim20, chim21, chim22, chim23, chim24, chim25, chim26, chim27, chim28, chim29, chim30, chim31, chim32, chim33, chim34, chim35, chim36, chim37, chim38, chim39, chim40, chim41, chim42, chim43, chim44, chim45, chim46, chim47, chim48, chim49, chim50, chim51, chim52, chim53, chim54, chim55, chim56, chim57, chim58, chim59, chim60, chim61, chim62, chim63, chim64, chim65, chim66, chim67, chim68, chim69, chim70, chim71, chim72, chim73, chim74, chim75, chim76, chim77, chim78, chim79, chim80, chim81, chim82, chim83, chim84, chim85, chim86};
        String[][] fizica = new String[][] { fiz1, fiz2, fiz3, fiz4, fiz5, fiz6, fiz7, fiz8, fiz9, fiz10, fiz11, fiz12, fiz13, fiz14, fiz15, fiz16, fiz17, fiz18, fiz19, fiz20, fiz21, fiz22, fiz23, fiz24, fiz25, fiz26, fiz27, fiz28, fiz29, fiz30, fiz31, fiz32, fiz33, fiz34, fiz35, fiz36, fiz37, fiz38, fiz39, fiz40, fiz41, fiz42, fiz43, fiz44, fiz45, fiz46, fiz47, fiz48, fiz49};

        if(Objects.equals(category, "chimie")){
            int randomNum = ThreadLocalRandom.current().nextInt(0, chimie.length);
            return chimie[randomNum];
        }else{
            if (Objects.equals(category, "Electricitate") || Objects.equals(category, "Fizica optica") || Objects.equals(category, "Termodinamica") || Objects.equals(category, "fizica")){
                int randomNum = ThreadLocalRandom.current().nextInt(0, fizica.length);
                return fizica[randomNum];
            }else {
                int randomNum = ThreadLocalRandom.current().nextInt(0, biologie.length);
                return biologie[randomNum];
            }
        }
    }
    /**
     * Called when the user taps the Raspunde button
     */
    public void buttonRaspunde(View view) {

        boolean isChecked1 = ((RadioButton) findViewById(R.id.radioButton1)).isChecked();
        boolean isChecked2 = ((RadioButton) findViewById(R.id.radioButton2)).isChecked();
        boolean isChecked3 = ((RadioButton) findViewById(R.id.radioButton3)).isChecked();
        boolean isChecked4 = ((RadioButton) findViewById(R.id.radioButton4)).isChecked();
        boolean isChecked5 = ((RadioButton) findViewById(R.id.radioButton5)).isChecked();

        int checked = 0;
        if (isChecked1) checked = 1;
        if (isChecked2) checked = 2;
        if (isChecked3) checked = 3;
        if (isChecked4) checked = 4;
        if (isChecked5) checked = 5;

        TextView feedback = (TextView) findViewById(R.id.feedback);

        if (answer == checked) {
            feedback.setTextColor(Color.rgb(0, 204, 0));
            feedback.setText("Răspuns corect!");

            findViewById(R.id.radioButton1).setEnabled(false);
            findViewById(R.id.radioButton2).setEnabled(false);
            findViewById(R.id.radioButton3).setEnabled(false);
            findViewById(R.id.radioButton4).setEnabled(false);
            findViewById(R.id.radioButton5).setEnabled(false);
            findViewById(R.id.buttonRaspunde).setVisibility(View.INVISIBLE);
            findViewById(R.id.buttonUrmatoarea).setVisibility(View.VISIBLE);

        }
        if (answer != checked) {
            feedback.setTextColor(Color.rgb(200, 0, 0));
            feedback.setText("Răspuns gresit! Mai încearcă");
        }
        if (checked == 0) {
            feedback.setTextColor(Color.rgb(200, 0, 0));
            feedback.setText("Selectați un răspuns!");
        }
    }

    /**
     * Called when the user taps the Urmatoare button
     */
    public void buttonUrmatoarea(View view) {

        Intent intent = new Intent(this, RandomQuestion.class);
        intent.putExtra("category", category);
        intent.putExtra("year", year);
        intent.putExtra("theme", theme_set);
        startActivity(intent);
        finish();
    }

}

