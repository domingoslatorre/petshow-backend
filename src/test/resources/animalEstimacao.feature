# language: pt
Funcionalidade: Animal de estimacao
    Delineacao do Cenario: Operacao bem sucedido
        Dado que acesso <endereco><variavel> adequadamente com metodo <metodo> e corpo <corpo>
        Entao devo receber como retorno um status <statusHttp>
        E a resposta deve ser <resposta>
        Exemplos:
            | endereco              | variavel | metodo  | statusHttp | corpo                                 | resposta                                                           |
            | '/animalEstimacao/'   | ''       |'POST'   | 201        | 'AnimalEstimacaoRepresentation'       | '{\"id\": null, \"nome\": null, \"foto\": null, \"tipo\": null}'   |
            | '/animalEstimacao/'   | '1'      |'GET'    | 200        | ''                                    | '{\"id\": null, \"nome\": null, \"foto\": null, \"tipo\": null}'   |
            | '/animalEstimacao/'   | ''       |'GET'    | 200        | ''                                    | '[{\"id\": null, \"nome\": null, \"foto\": null, \"tipo\": null}]' |
            | '/animalEstimacao/'   | '1'      |'PUT'    | 200        | 'AnimalEstimacaoRepresentation'       | '{\"id\": null, \"nome\": null, \"foto\": null, \"tipo\": null}'   |
            | '/animalEstimacao/'   | '1'      |'DELETE' | 200        | ''                                    | '{\"id\": null, \"status\": null, \"mensagem\": null}'                                 |