### API/Relay Demo
A implementação utiliza Redis para realizar um cache dos itens requisitados.
O tempo de validade dos itens cacheados pode ser ajustado em *ApiConf.CACHE_TTL*
São disponibilizados 4 endpoints.
```
Um para consulta por id do hotel: GET /api/hotel/byId/
Parâmetros: hotelId, checkin, checkout, adults, childs
Exemplo: GET /api/hotel/byId/?hotelId=4&checkin=2019-01-01&checkout=2019-01-10&adults=3&childs=2
```
```
Um para consulta por código da cidade: GET /api/hotel/byCityCode/
Parâmetros: cityCode, checkin, checkout, adults, childs
Exemplo: GET /api/hotel/byCityCode/?cityCode=4&checkin=2019-01-01&checkout=2019-01-10&adults=3&childs=2
```
```
Um que permite realizar o pré carregamento do hotel para o cache:
/api/hotel/preFetch/byId/?hotelId=4
```
```
Um que permite realizar o pré carregamento dos hotéis de uma cidade para o cache:
/api/hotel/preFetch/byCityCode/?cityCode=1032
```
Ao buscar hotel ou hotéis de uma cidade da API remota estes serão armazenados em cache para posterior utilização em requisições 
subsequentes dentro do intervalo configurado para valide do objeto.
Os endpoints de preFetch poderão ser utilizados para realizar o pré carregamento ativo de acordo com critérios de relevância. Mantendo os 
dados das cidades mais acessadas pré cacheados.

### Versão Serveless AWS Lambda
Uma versão adaptada para lambda está disponível no branch aws-lambda https://github.com/artusrocha/viagens-api/tree/aws-lambda
Para deploy com aws-cli:
```bash
cd viagens-api
git checkout aws-lambda
mvn clean && mvn package
aws --profile [YOUR_PROFILE] cloudformation package --template-file sam.yaml --output-template-file output-sam.yaml --s3-bucket [BUCKET]
aws cloudformation deploy --template-file output-sam.yaml --stack-name VIAGENSAPI --profile [YOUR_PROFILE] --capabilities CAPABILITY_IAM
```

#### Débito técnico
- Necessário Melhorias nos testes existentes
- Necessário Melhorias na cobertura dos testes incluindo a camada Resource
- Melhorias no mecanismo de gerenciamento do cache para que utilize o gerenciamento de TTL nativo do Redis.

#### Outras possíveis melhorias na implementação
- Criar registro de score por cidade, para que o próprio sistema possa determinar quais as cidades mais acessadas e assim gerenciar o pré 
carregamento da API remota.
- Talvez usar @Scheduled para o pré carregamento da API remota


