# Applicazione Web società finanziaria
Repository github per progetto del corso di Ingegneria del Software A.A. 2020/2021 alla Federico II di Napoli.


### Traccia del Progetto
<details>
  <summary>Clicca per espandere/comprimere</summary>
  
**Applicazione Web società finanziaria**\
Bloomfin è una società di intermediazione finanziaria che ha commissionato la realizzazione di
una applicazione web per le proprie attività. L'azienda è costituita da broker finanziari che
aiutano i risparmiatori a investire i loro patrimoni in borsa.\
I broker interagiscono con il sistema per consultare il valore di titoli azionari oppure per creare
pacchetti finanziari: i pacchetti finanziari, che costituiscono una fonte di investimento per i
risparmiatori, sono un insieme di titoli che possiedono un buon mix di livello di rendimento e
rischio. I risparmiatori visitano il sito web per acquistare i pacchetti finanziari proposti da
Bloomfin oppure per consultare il valore dei pacchetti in loro possesso. All'atto della
consultazione del valore dei propri pacchetti finanziari, un risparmiatore che non sia soddisfatto
del rendimento dell'investimento, può infine disporne la vendita.\
L'applicazione deve avere informazioni riguardo i broker registrati nel sistema e deve
memorizzare il loro nome, cognome, data di nascita e numero di registrazione alla camera di
commercio; e riguardo i risparmiatori, di cui deve memorizzare il loro nome, cognome, indirizzo
di residenza, codice fiscale e data di nascita.\
In gergo finanziario, sia i titoli azionari sia i pacchetti finanziari sono asset economici, che
Bloomfin gestisce e che l'applicazione deve memorizzare: gli asset posseggono un identificativo
univoco nel sistema. I titoli azionari, oltre all’identificativo, hanno un valore per azione e sono
registrati presso una borsa. Delle borse occorre avere informazione del loro orario di apertura e
di chiusura, del loro nome e dello stato di appartenenza. I pacchetti finanziari sono composti da
titoli azionari e posseggono, oltre all’identificativo, il fattore di rischio, una stima di rendimento, e
il numero di azioni per titolo di cui il pacchetto è composto. I pacchetti finanziari possono essere
acquistati da uno o più risparmiatori. Un risparmiatore può scegliere di acquistare più unità di
uno stesso pacchetto finanziario e, all'acquisto, il sistema deve memorizzare la data e la
quantità desiderata.\
Infine il sistema deve gestire le quotazioni: ogni minuto l'applicazione deve contattare le borse
per aggiornare i valori associati ai titoli azionari. I broker possono chiedere al sistema di
visionare il valore di un asset finanziario, che è richiesto al sistema tramite l’identificativo: se l’id
corrisponde ad un titolo azionario, il sistema fornisce l’ultimo aggiornamento del valore per
azione; mentre, se l’id corrisponde a un pacchetto finanziario, il sistema deve calcolare il valore
del pacchetto, che è pari al prodotto della somma dei valori di tutti i titoli azionari di cui un
pacchetto finanziario è composto per il numero di azioni per titolo del pacchetto.
</details>

### **Assunzioni**
<details>
  <summary>Clicca per espandere/comprimere</summary>
  
Abbiamo fatto varie assunzioni interpretando la traccia:
1. Un broker può consultare tutti i pacchetti esistenti e non solo quelli che ha creato.
2. I titoli azionari possono appartenere a una sola borsa, per non complicare maggiormente le relazioni UML e le tabelle SQL.
3. I titoli hanno "infinite azioni", non c'è un controllo sul massimo numero di azioni di un titolo che possono essere inserite in un pacchetto o comprate.
  
</details>

### **Modello di ciclo di vita**
<details>
  <summary>Clicca per espandere/comprimere</summary>
  
Il modello di ciclo di vita che abbiamo scelto di seguire nello sviluppo di questo progetto è il modello a cascata.
  
</details>

### **Requisiti Utente**
<details>
  <summary>Clicca per espandere/comprimere</summary>
  
 <details>
  <summary>Legenda</summary>
  
| Sigla | Tipologia Requisito |
| --- | --- |
| PER | Persistenza |
| FUN | Funzionale |

</details>

DATI1 PER \<must> - Il sistema deve memorizzare tutte le informazioni riguardanti i broker registrati per garantirne la persistenza.\
DATI2 PER \<must> - Il sistema deve memorizzare tutte le informazioni riguardanti i risparmiatori registrati per garantirne la persistenza.\
DATI3 PER \<must> - Il sistema deve memorizzare gli asset finanziari.\
DATI4 PER \<must> - Il sistema deve memorizzare tutte le informazioni riguardanti le borse.\
DATI5 PER \<must> - Il sistema deve memorizzare la data e la quantità di ogni acquisto.\
FUNZ1 FUN \<must> - I broker devono poter consultare il valore di titoli azionari.\
FUNZ2 FUN \<must> - I broker devono poter creare pacchetti finanziari.\
FUNZ3 FUN \<must> - I risparmiatori devono poter acquistare i pacchetti finanziari proposti.\
FUNZ4 FUN \<must> - I risparmiatori devono poter consultare il valore dei pacchetti azionari in loro possesso.\
FUNZ5 FUN \<should> - I risparmiatori devono poter vendere i pacchetti finanziari posseduti.\
FUNZ6 FUN \<must> - Il sistema deve aggiornare i valori associati ai titoli azionari ogni minuto.\
FUNZ7 FUN \<must> - Il sistema deve saper calcolare il valore di un pacchetto finanziario.

</details>

### **Diagramma Casi d'Uso**
<details>
  <summary>Clicca per espandere/comprimere</summary>
  
![diagramma_casi](https://github.com/IS-unina/canale-san-giovanni-team_4/blob/master/Diagrammi_UML/diagramma_casi_uso.png)

</details>

### **Tabelle specifiche dei casi d'uso**
<details>
  <summary>Clicca per espandere/comprimere</summary>


**Nota**: Il campo 'attori secondari' è stato omesso in quanto non necessario. Non c'è interazione diretta tra broker e risparmiatori negli scenari dei casi d'uso.

| Caso d'uso | Creazione Pacchetti Finanziari |
| --- | --- |
| id | creazione_pacchetto |
| descrizione | Il broker crea un nuovo pacchetto finanziario. |
| attori primari | Broker |
| precondizioni | Il broker è registrato nel sistema.<br>I titoli azionari sono registrati nel sistema. |
| sequenza di azioni | 1. Il broker sceglie di creare il nuovo pacchetto.<br>2. Il broker seleziona i titoli da aggiungere al pacchetto.<br>3. Il sistema memorizza il pacchetto. |
| postcondizioni | Il pacchetto finanziario è stato creato e memorizzato. |

| Caso d'uso | Acquisto Pacchetti Finanziari |
| --- | --- |
| id | acquisto_pacchetto |
| descrizione | Il risparmiatore acquista una o più unità di un pacchetto finanziario. |
| attori primari | Risparmiatore |
| precondizioni | Il risparmiatore è registrato al sistema. |
| sequenza di azioni | 1. Il risparmiatore sceglie di acquistare il pacchetto.<br>2. Il risparmiatore seleziona il pacchetto.<br>3. Il risparmiatore seleziona la quantità di unità del pacchetto da acquistare.<br>4. Il sistema registra l’acquisto. |
| postcondizioni | La transazione è memorizzata. |

| Caso d'uso | Registrazione |
| --- | --- |
| id | registrazione |
| descrizione | L'utente si registra al sistema. |
| attori primari | Utente |
| precondizioni | nessuna |
| sequenza di azioni | 1. L'utente richiede di registrarsi al sistema.<br>2. Il sistema chiede di inserire indirizzo e-mail e password.<br>3. Il sistema chiede l’inserimento di nome, cognome, data di nascita.<br>4. Se l’utente è un broker il sistema chiede l’inserimento del numero di registrazione alla camera di commercio.<br>5. Se l’utente è un risparmiatore il sistema chiede l’inserimento di codice fiscale e indirizzo di residenza.<br>6. Il sistema memorizza l’utente. |
| postcondizioni | L'utente è registrato nel sistema. |

| Caso d'uso | Vendita Pacchetti Finanziari |
| --- | --- |
| id | vendita_pacchetto |
| descrizione | Il risparmiatore vende un pacchetto finanziario in suo possesso. |
| attori primari | Risparmiatore |
| precondizioni | Il pacchetto finanziario è registrato nel sistema.<br>Il risparmiatore è registrato nel sistema e ha precedentemente acquistato il pacchetto. |
| sequenza di azioni | 1. Il risparmiatore consulta il pacchetto.<br>2. Il risparmiatore richiede di vendere il pacchetto.<br>3. Il sistema dispone la vendita del pacchetto. |
| postcondizioni | Il risparmiatore non è più in possesso del pacchetto. |

| Caso d'uso | Consultazione Titoli Azionari |
| --- | --- |
| id | consultazione_titoli |
| descrizione | L’utente (o il sistema) consulta i titoli finanziari. |
| attori primari | Broker/nessuno |
| precondizioni | L'utente è registrato nel sistema ed è un broker.<br>Almeno un titolo azionario è registrato nel sistema. |
| sequenza di azioni | 1. L’utente (o il sistema) richiede di consultare il valore dei titoli.<br>2. Il sistema fornisce per ogni titolo l’ultimo aggiornamento del valore per azione. |
| postcondizioni | L'utente riceve il valore dei titoli. |

| Caso d'uso | Consultazione Pacchetti Finanziari |
| --- | --- |
| id | consultazione_pacchetto |
| descrizione | L’utente consulta un pacchetto finanziario. |
| attori primari | Utente |
| precondizioni | L'utente è registrato al sistema.<br>Il pacchetto finanziario è registrato nel sistema.<br>Se l'utente è un risparmiatore deve possedere il pacchetto di interesse. |
| sequenza di azioni | 1. L’utente richiede di consultare il valore del pacchetto.<br>2. Il sistema consulta i titoli azionari \[includendo _consultazione_titolo_].<br>3. Il sistema effettua la somma del valore dei titoli che compongono il pacchetto.<br>4. Il sistema moltiplica il valore trovato per il numero di azioni per titolo.<br>5. Il sistema fornisce il valore. |
| postcondizioni | L'utente riceve il valore del pacchetto d'interesse. |

| Caso d'uso | Aggiornamento Valore Titoli |
| --- | --- |
| id | aggiorna_titoli |
| descrizione | Il sistema aggiorna periodicamente il valore dei titoli finanziari.<br>Periodo: 1min |
| attori primari | Tempo |
| precondizioni | I titoli sono registrati nel sistema.<br>La borsa è registrata nel sistema ed aperta. |
| sequenza di azioni | 1. Il sistema contatta le borse.<br>2. Il sistema aggiorna il valore dei titoli. |
| postcondizioni | I valori dei titoli sono aggiornati. |

</details>

### **Diagramma delle Classi di Analisi**
<details>
  <summary>Clicca per espandere/comprimere</summary>
  
**Nota**: 'Pacchetto Azionario' è utilizzato come sinonimo di 'Pacchetto Finanziario'.
![diagramma_classi](https://github.com/IS-unina/canale-san-giovanni-team_4/blob/master/Diagrammi_UML/diagramma_classi_analisi.png)

</details>

### **Diagrammi di Sequenza di Analisi**
<details>
  <summary>Clicca per espandere/comprimere</summary>
  
  **Creazione Pacchetti Finanziari**:\
  ![](https://github.com/IS-unina/canale-san-giovanni-team_4/blob/master/Diagrammi_UML/Sequenze_Analisi/creazione_pacchetto.png)\
  **Acquisto Pacchetti Finanziari**:\
  ![](https://github.com/IS-unina/canale-san-giovanni-team_4/blob/master/Diagrammi_UML/Sequenze_Analisi/acquisto_pacchetto.png)\
  **Registrazione**:\
  ![](https://github.com/IS-unina/canale-san-giovanni-team_4/blob/master/Diagrammi_UML/Sequenze_Analisi/registrazione.png)\
  **Vendita Pacchetti Finanziari**:\
  ![](https://github.com/IS-unina/canale-san-giovanni-team_4/blob/master/Diagrammi_UML/Sequenze_Analisi/vendita_pacchetto.png)\
  **Consultazione Titoli Azionari**:\
  ![](https://github.com/IS-unina/canale-san-giovanni-team_4/blob/master/Diagrammi_UML/Sequenze_Analisi/consultazione_titolo.png)\
  **Consultazione Pacchetti Finanziari**:\
  ![](https://github.com/IS-unina/canale-san-giovanni-team_4/blob/master/Diagrammi_UML/Sequenze_Analisi/consultazione_pacchetto.png)\
  **Aggiornamento Valore Titoli**:\
  ![](https://github.com/IS-unina/canale-san-giovanni-team_4/blob/master/Diagrammi_UML/Sequenze_Analisi/aggiorna_titoli.png)
  
</details>

### **Diagramma delle Classi Avanzato**
<details>
  <summary>Clicca per espandere/comprimere</summary>
  
**Cambiamenti rispetto al diagramma semplice:**
* Aggiunta delle molteplicità di tutte le relazioni
* Separazione delle responsabilità con l'approccio BCE, introducendo un gestore dell'applicazione e delle classi boundary di interfacciamento degli attori con esso

![diagramma_classi_adv](https://github.com/IS-unina/canale-san-giovanni-team_4/blob/master/Diagrammi_UML/diagramma_classi_avanzato.png)

</details>

### **Diagrammi di Sequenza Avanzati**
<details>
  <summary>Clicca per espandere/comprimere</summary>
  
  **Creazione Pacchetti Finanziari**:\
  ![](https://github.com/IS-unina/canale-san-giovanni-team_4/blob/master/Diagrammi_UML/Sequenze_Avanzate/creazione_pacchetto.png)\
  **Acquisto Pacchetti Finanziari**:\
  ![](https://github.com/IS-unina/canale-san-giovanni-team_4/blob/master/Diagrammi_UML/Sequenze_Avanzate/acquisto_pacchetto.png)\
  **Vendita Pacchetti Finanziari**:\
  ![](https://github.com/IS-unina/canale-san-giovanni-team_4/blob/master/Diagrammi_UML/Sequenze_Avanzate/vendita_pacchetto.png)\
  **Consultazione Titoli Azionari**:\
  ![](https://github.com/IS-unina/canale-san-giovanni-team_4/blob/master/Diagrammi_UML/Sequenze_Avanzate/consultazione_titolo.png)
  
</details>

### **Diagramma delle Classi di Progettazione**
<details>
  <summary>Clicca per espandere/comprimere</summary>
  
![diagramma_classi_prog](https://github.com/IS-unina/canale-san-giovanni-team_4/blob/master/Diagrammi_UML/diagramma_classi_progettazione.png)

</details>

### **Diagrammi di Sequenza di Progettazione**
<details>
  <summary>Clicca per espandere/comprimere</summary>
  
  **Creazione Pacchetti Finanziari**:\
  ![](https://github.com/IS-unina/canale-san-giovanni-team_4/blob/master/Diagrammi_UML/Sequenze_Progettazione/creazione_pacchetto.png)\
  **Acquisto Pacchetti Finanziari**:\
  ![](https://github.com/IS-unina/canale-san-giovanni-team_4/blob/master/Diagrammi_UML/Sequenze_Progettazione/acquisto_pacchetto.png)\
  **Vendita Pacchetti Finanziari**:\
  ![](https://github.com/IS-unina/canale-san-giovanni-team_4/blob/master/Diagrammi_UML/Sequenze_Progettazione/vendita_pacchetto.png)\
  **Consultazione Titoli Azionari**:\
  ![](https://github.com/IS-unina/canale-san-giovanni-team_4/blob/master/Diagrammi_UML/Sequenze_Progettazione/consultazione_titolo.png)
  
</details>

### **Inizializzazione SQL**
<details>
  <summary>Clicca per espandere/comprimere</summary>
  
L'applicazione funziona correttamente ed è eseguibile dal file Bloomfin/src/boundary/Console.java, prima di usarla bisogna però inizializzare il database h2 (Utente: sa, nessuna password) con il seguente codice: 

```
DROP TABLE ComposizionePacchetti;
DROP TABLE AppartenenzaTitoli;
DROP TABLE PacchettiCreati;
DROP TABLE Transazioni;
DROP TABLE Pacchetti;
DROP TABLE Borse;
DROP TABLE Titoli;
DROP TABLE Broker;
DROP TABLE Risparmiatori;
          
CREATE TABLE Broker (
	Id LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
	NumeroCameraCommercio VARCHAR(255),
	Nome VARCHAR(255),
	Cognome VARCHAR(255),
	DataNascita DATE,
	Email VARCHAR(255),
	Password VARCHAR(255),
	UNIQUE KEY Broker_Email_Unique (Email) );
	        
CREATE TABLE Risparmiatori (
	Id LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
	CodiceFiscale VARCHAR(16),
	Nome VARCHAR(255),
	Cognome VARCHAR(255),
	DataNascita DATE,
	IndirizzoResidenza VARCHAR(255),
	Email VARCHAR(255),
	Password VARCHAR(255),
	UNIQUE KEY Risparmiatori_Email_Unique (Email) );
	        
CREATE TABLE Borse (
	Nome VARCHAR(255) NOT NULL PRIMARY KEY,
	StatoAppartenenza VARCHAR(255),
	OrarioApertura TIME,
	OrarioChiusura TIME );
	        
CREATE TABLE Pacchetti (
	Id LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
	FattoreRischio FLOAT,
	StimaRendimento FLOAT );
	        
CREATE TABLE Titoli (
	Id LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
	ValoreAzione FLOAT );
	        
CREATE TABLE Transazioni (
	Id LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
	Quantita INTEGER,
	Data DATE,
	IdProprietario LONG,
	IdPacchetto LONG,
	FOREIGN KEY (IdProprietario) REFERENCES Risparmiatori (Id),
	FOREIGN KEY (IdPacchetto) REFERENCES Pacchetti (Id));

CREATE TABLE PacchettiCreati (
	IdBroker LONG,
	IdPacchetto LONG,
	FOREIGN KEY (IdBroker) REFERENCES Broker (Id),
	FOREIGN KEY (IdPacchetto) REFERENCES Pacchetti (Id),
	PRIMARY KEY(IdBroker,IdPacchetto) );

CREATE TABLE AppartenenzaTitoli (
	IdTitolo LONG,
	NomeBorsa VARCHAR(255),
	FOREIGN KEY (IdTitolo) REFERENCES Titoli (Id),
	FOREIGN KEY (NomeBorsa) REFERENCES Borse (Nome),
	PRIMARY KEY(IdTitolo,NomeBorsa) );

CREATE TABLE ComposizionePacchetti (
	IdPacchetto LONG,
	IdTitolo LONG,
	FOREIGN KEY (IdTitolo) REFERENCES Titoli (Id),
	FOREIGN KEY (IdPacchetto) REFERENCES Pacchetti (Id),
	NumeroAzioni INT,
	PRIMARY KEY(IdPacchetto,IdTitolo) );
          
INSERT INTO Borse (Nome, StatoAppartenenza, OrarioApertura, OrarioChiusura) VALUES ('Borsa di Milano', 'Italia', '08:00:00', '17:30:00');
INSERT INTO Borse (Nome, StatoAppartenenza, OrarioApertura, OrarioChiusura) VALUES ('Borsa di Zurigo', 'Svizzera', '08:00:00', '18:00:00');
INSERT INTO Borse (Nome, StatoAppartenenza, OrarioApertura, OrarioChiusura) VALUES ('Borsa di Parigi', 'Francia', '07:00:00', '16:30:00');

	        
INSERT INTO Titoli (ValoreAzione) VALUES (1);
INSERT INTO Titoli (ValoreAzione) VALUES (1.3);
INSERT INTO Titoli (ValoreAzione) VALUES (1.5);

INSERT INTO Titoli (ValoreAzione) VALUES (2);
INSERT INTO Titoli (ValoreAzione) VALUES (2.3);
INSERT INTO Titoli (ValoreAzione) VALUES (3.8);

INSERT INTO Titoli (ValoreAzione) VALUES (0.5);
INSERT INTO Titoli (ValoreAzione) VALUES (5.6);
INSERT INTO Titoli (ValoreAzione) VALUES (4.4);
	       
	        
INSERT INTO AppartenenzaTitoli (IdTitolo, NomeBorsa) VALUES (1 , 'Borsa di Milano');
INSERT INTO AppartenenzaTitoli (IdTitolo, NomeBorsa) VALUES (2 , 'Borsa di Milano');
INSERT INTO AppartenenzaTitoli (IdTitolo, NomeBorsa) VALUES (3 , 'Borsa di Milano');

INSERT INTO AppartenenzaTitoli (IdTitolo, NomeBorsa) VALUES (4 , 'Borsa di Zurigo');
INSERT INTO AppartenenzaTitoli (IdTitolo, NomeBorsa) VALUES (5 , 'Borsa di Zurigo');
INSERT INTO AppartenenzaTitoli (IdTitolo, NomeBorsa) VALUES (6 , 'Borsa di Zurigo');

INSERT INTO AppartenenzaTitoli (IdTitolo, NomeBorsa) VALUES (7 , 'Borsa di Parigi');
INSERT INTO AppartenenzaTitoli (IdTitolo, NomeBorsa) VALUES (8 , 'Borsa di Parigi');
INSERT INTO AppartenenzaTitoli (IdTitolo, NomeBorsa) VALUES (9 , 'Borsa di Parigi');
```

</details>
