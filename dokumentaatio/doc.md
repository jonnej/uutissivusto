Aiheen kuvaus
=============================
Tässä projektissa on tarkoitus luoda uutissivusto, jossa käyttäjä (writer) voi lisätä ja
muokata uutisia. Uutisten selaamiseen tulee toimintoja mm. kategorian perusteella
etsiminen.
----------
Projekti on Web-palvelinohjelmointi (java) kurssin harjoitustyö ja se toteutetaan
Javalla, Spring:llä ja mm. Herokua käyttäen.
==============================
Käyttötapaukset
-----------------------
1. Käyttäjän(writer) rekisteröinti/sisäänkirjautuminen
- Käyttäjä haluaa kirjautua/rekisteröityä sivustolle, joka mahdollistaa uutisten kirjoittamisen.
- Jos ei jo kirjautuneena, sivustolla näkyy oikeassa yläkulmassa login/register linkki, josta pääsee /login sivulle.
- Jos login/register onnistuu kirjautuu käyttäjä sisään sivustolle.
2. Käyttäjä kirjoittaa uuden uutisen.
- Käyttäjän pitää olla kirjautunut sisään nähdäkseen add article linkin navigaatio palkissa.
- Linkki ohjautuu uutisen teko sivulle, jossa annetaan otsikko(headline), ingerssi(lead text), uutisen teksti (textareassa), lisätään kuva, annetaan vähintään yksi kirjoittaja (writer) ja kategoria (category).
* Jos jokin paitsi kuva puuttuu, ohjautuu takaisin ja kertoo mitä vaaditaan.
- Onnistuessa uutinen lisätään ja ohjataan käyttäjä etusivulle.
3. Käyttäjä haluaa editoida uutista.
- Käyttäjä voi editoida uutista, jos hän on yksi sen kirjoittajista.
- Linkki editointiin löytyy uutisen omalta sivulta.
- Muokkaaminen tapahtuu samoin kuin uuden luominen.
- Onnistuessaan käyttäjä ohjataan etusivulle.
4. Käyttäjä (ei tarvitse olla kirjautunut) haluu selata uutisia tietystä kategoriasta.
- Sivuston navigaatiopalkissa listataan sivustolla käytössä olevat kategoriat.
- Linkkiä klikkaamalla käyttäjä siirtyy kategorian omalle sivulle, jossa on lista uutisista, jotka kuuluvat kyseiseen kategoriaan.

===========================
Käyttöohje
--------------------
Sivustolla liikutaan linkkejä klikkaamalla. Myös osoitteiden kirjoittaminen suoraan on mahdollista, jos sivu löytyy. Kirjoittaakseen uutisen, pitää käyttäjän olla rekisteröitynyt/kirjautunut sivustolle. Tämä tapahtuu navigaatiopalkissa löytyvän login/register linkin takaa. Valmiit käyttäjätunnukset: Name: Jonne password: jonne JA Name: Krist password: Krist

============================
Puuttuvat ominaisuudet ja muut puutteet
---------------------
