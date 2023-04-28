# Sklad

# Pozadavky: 
Sklad + nákupní košík.

Aplikace musí mít GUI, které je nutné napsat ručně.

Při vývoji respektovat MVC (použití JTable, AbstractTableModel).

Položky skladu, např.: název zboží, cenu, počet, kategorie.
- Přidávání, odebírání, editace, ukládání/načítání.
- Řazení položek podle sloupce.
- Filtrování podle kategorie.

Položky nákupního košíku: název, počet.
- Přidávání, odebírání.
- Zobrazovat aktuální celkovou cenu.
- Vytvoření účtenky.

Ser/deser min. do dvou formátů: JSON a některý z csv, ObjectStream, text ap.
- Pro formát JSON je povoleno a doporučeno použít externí knihovnu JSON.

SW by se měl chovat rozumným způsobem, tj. nedovolit nesmyslné chování.
Před ukončením upozornit uživatele na případně neuložený stav.
