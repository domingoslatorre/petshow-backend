Feature: Cadastro de animais
    Scenario: Cadastro bem sucedido
        Given que enviei para o serviço os campos corretamente
        When devo receber como retorno um status 200
        Then receber o animal de estimação no corpo da resposta

    Scenario: Recuperação de lista
        Given que fiz solicitação para endpoint com lista de animais
        Then devo receber como retorno um status 200
        And receber uma lista de animais de estimação no corpo da resposta

    Scenario: Edição de animais
        Given que enviei para os serviços algum animal com algum campo diferente do original
        Then devo receber como retorno um status 200
        And receber o animal com os campos editados

    Scenario: Deleção de animais
        Given que fiz solicitação para deletar um animal
        Then devo receber como retorno um status 204
        And receber o valor True