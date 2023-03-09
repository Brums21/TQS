### Resposta às questões do Lab3, exercício 1

#### Alinea a) 

![image](https://user-images.githubusercontent.com/75539238/224169594-432b92f2-819f-4890-8b6d-3765fac8b5a8.png)
(Teste A)

![image](https://user-images.githubusercontent.com/75539238/224169835-1cd0124f-6120-4b33-8d9f-7a1dbf2fb55d.png)
(Teste B)

![image](https://user-images.githubusercontent.com/75539238/224169933-001a439b-8f1f-45f3-9741-a2269b9883c8.png)
(Teste E)

Estes exercetos de código implementam a chamada de métodos expressivos em cadeia.

#### Alinea b)

No exemplo C, o comportamento do repositótio é _mocked_, e, por isso, evita a utilização de uma base de dados. Isto é, o serviço que faz a ligação ao repositório é _mocked_, sendo atribuidos valores esperados para um determinado input, pelo que a base de dados não necessita de ser implementada para o teste ao REST controller seja efetuado. 

#### Alinea c) What is the difference between standard @Mock and @MockBean?

Ambos @Mock e @MockBean são utilizados de modo a recriarem o comportamento de uma determinada entidade. No entanto, o @MockBean é utilizado para recriar o comportamento de um determinado Bean (Spring Boot Context).

#### Alinea d) 

O ficheiro “application-integrationtest.properties” é utilizado de forma a que as propriedades do Spring Boot sejam sobrepostas aquando da criação e execução de testes. 
Por exemplo, a especificação de uma determinada base de dados será alterada aquando da realização de testes, o que é util, uma vez que se pode criar uma base de dados fictícia e ligar a mesma através desse ficheiro, fazendo com os testes no repositório não alterem a base de dados pré existente e funcional.

#### Alinea e)

No exemplo C/, a API é mocked na sua totalidade, sendo replicada o seu comportamento (implementação do MockMVC), o que permite não ter de utilizar a base de dados. 
Já os exemplos D/ e E/ são semelhantes, sendo que a principal diferença consiste no facto de D/ utilizar o MockMVC para ter um servelet para teste, e E/ implementar TestRestTemplate

