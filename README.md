# Alphalytics

7. Visualise la base de données H2
Ouvre ton navigateur :
http://localhost:8080/h2-console
Connecte-toi avec :

JDBC URL : jdbc:h2:mem:alphalytics
User Name : sa
Password : (laisse vide)

DTO = objet simple qui représente exactement ce que l'API envoie/reçoit

Client (JSON) → BacktestRequest (DTO) → Service → Backtest (Entity) → DB

DB → Backtest (Entity) → Service → BacktestResponse (DTO) → Client (JSON)

1. Le client envoie une BacktestRequest (via l'API)
   ↓
2. Le controller reçoit la requête et appelle le service
   ↓
3. Le service transforme BacktestRequest → Backtest (entité JPA)
   ↓
4. Le service sauvegarde en DB via le repository
   ↓
5. Le service transforme Backtest (entité) → BacktestResponse (DTO)
   ↓
6. Le controller renvoie BacktestResponse au client (JSON)

### Contexte métier

Une **stratégie de trading** définit **quand acheter et quand vendre**.

**Exemples simples :**
- "J'achète quand le prix descend sous 100$, je vends quand il monte au-dessus de 120$"
- "J'achète quand la moyenne des 10 derniers jours croise au-dessus de la moyenne des 50 derniers jours"

**Une stratégie = un algorithme qui analyse les prix et génère des signaux.**

### Les signaux

Un signal = une décision :
- **BUY** : Acheter maintenant
- **SELL** : Vendre maintenant
- **HOLD** : Ne rien faire


###################################

1. Client envoie une requête
   POST /api/v1/backtests
   {
     "symbol": "AAPL",
     "strategyName": "sma_crossover",
     "strategyParams": "{\"short\": 10, \"long\": 50}",
     "startDate": "2024-01-01",
     "endDate": "2024-12-31",
     "initialCash": 10000
   }
   
2. BacktestController reçoit la requête
   → Appelle BacktestService.createBacktest()
   
3. BacktestService fait:
   a) Télécharge les données de marché
      → marketDataService.getHistoricalData()
      → Retourne List<StockData> (avec open/high/low/close)
   
   b) Instancie la stratégie selon le nom
      → Si "sma_crossover" : new SMAStrategy(10, 50)
      → Si "rsi" : new RSIStrategy(14)
   
   c) Génère les signaux
      → strategy.generateSignals(stockData)
      → Retourne List<Signal> (BUY/SELL/HOLD pour chaque jour)
   
   d) Exécute le backtest (moteur de backtest)
      → backtestEngine.run(signals, initialCash)
      → Simule les trades
      → Calcule les métriques (return%, Sharpe, drawdown...)
   
   e) Sauvegarde en DB
      → backtestRepository.save(backtest)
   
4. Retourne le résultat au client