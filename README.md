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