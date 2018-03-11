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
                int min = output.length;
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

        String[][] biologie = new String[][] { bio1, bio2, bio3, bio4, bio5, bio6, bio7, bio8, bio9, bio10, bio11, bio12, bio13, bio14, bio15, bio16, bio17, bio18, bio19, bio20, bio21, bio22, bio23, bio24, bio25, bio26, bio27, bio28, bio29, bio30, bio31, bio32, bio33, bio34, bio35, bio36, bio37, bio38, bio39, bio40, bio41, bio42, bio43, bio44};
        String[][] chimie = new String[][] { chim1, chim2, chim3, chim4, chim5, chim6, chim7, chim8, chim9, chim10, chim11, chim12, chim13, chim14, chim15, chim16, chim17, chim18, chim19, chim20, chim21, chim22, chim23, chim24, chim25, chim26, chim27, chim28, chim29};
        String[][] fizica = new String[][] { fiz1, fiz2, fiz3, fiz4, fiz5, fiz6, fiz7, fiz8, fiz9, fiz10, fiz11, fiz12, fiz13, fiz14, fiz15, fiz16, fiz17};

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

