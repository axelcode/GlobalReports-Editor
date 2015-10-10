# globalreports-editor
L'editor visuale per disegnare graficamente e compilare i layout per il motore GlobalReport Core. 
Ma GlobalReports Editor può essere usato anche come editor per creare documenti in PDF, grazie all'engine integrato. Con le sue funzionalità relative al testo ed alla grafica, è possibile realizzare qualsiasi tipo di documento.


Elenco dei rilasci 
==================

**V.0.5.3** - 10/10/2015

Correzione dei seguenti bug:
- All'apertura di un nuovo documento veniva duplicata la barra degli strumenti.
- La cancellazione di una lista non liberava il suo riferimento dalla memoria.
- Se un oggetto veniva selezionato e si procedeva a muovere le ancore relative alle sezioni della pagina, l'applicativo andava in eccezione.

Aggiunte le seguenti features:
- La lista viene sempre disegnata sotto tutti gli altri oggetti.
- Implementata la funzione di aggiunta di un testo all'interno di un rettangolo, tramite menù popup.
- La tabella dele proprietà riconosce il tasto -RETURN nei campi di inserimento testo. Inoltre sposta il focus sulla row successiva.
- Lo splitter contenente la tabella delle proprietà si adatta automaticamente alla dimensione della tabella.

V.0.5.2 - 07/10/2015
--------------------

- Modificata la tabella delle proprietà: l'oggetto JTable è stato abbandonato ed al suo posto è ora presente una GRTable disegnata da zero e più adatta e fruibile dell'oggetto nativo di Java

V.0.5.1 - 03/10/2015
--------------------

- Problema relativo alle FUNCTION di formato: non venivano correttamente visualizzate in anteprima in quanto GlobalReports Editor modificava le parentesi delle function con il valore ottale. Corretto.
- Problema relativo alla modifica di una variabile: l'anteprima con xml dati non recepiva le modifiche del nome della variabile. Corretto.
- Il copia/incolla adesso posiziona l'oggetto clonato allineato sull'asse delle x e subito sotto l'oggetto padre. Inoltre il focus viene spostato sull'oggetto appena clonato.
