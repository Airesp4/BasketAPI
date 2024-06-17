document.addEventListener("DOMContentLoaded", function () {
    const selectFiltro = document.getElementById("select-filtro");
    const inputValor = document.getElementById("input-valor");
    const inputValor2 = document.getElementById("input-valor-2");
    const checkboxRelatorio = document.getElementById("checkbox-relatorio");
    const resultList = document.getElementById("result-list");

    selectFiltro.addEventListener("change", function () {
        if (selectFiltro.value === "jogador-ano" || selectFiltro.value === "jogadores-time") {
            inputValor2.style.display = "inline";
        } else {
            inputValor2.style.display = "none";
        }
    });

    const apiUrl = "/api";

    function filtrarDados(url, relatorio) {
        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao filtrar dados.');
                }
                return response.json();
            })
            .then(data => {
                resultList.innerHTML = "";

                data.forEach(item => {
                    const listItem = document.createElement("li");

                    listItem.textContent = `ID: ${item.id}, Nome: ${item.nome}, Idade: ${item.idade}, Minutos Jogados: ${item.minutosJogados} min, Total Pontos: ${item.pontos}, Time: ${item.time}, Temporada: ${item.temporada}`;
                    resultList.appendChild(listItem);
                });

                if (relatorio) {
                    console.log("Relatório gerado com os dados filtrados.");
                }
            })
            .catch(error => console.error("Erro ao filtrar dados:", error));
    }

    document.getElementById("btn-filtrar").addEventListener("click", function () {
        const filtro = selectFiltro.value;
        const valor = inputValor.value;
        const valor2 = inputValor2.value;
        const gerarRelatorio = checkboxRelatorio.checked;

        let url = "";

        switch (filtro) {
            case "jogador":
                url = `${apiUrl}/filtrarJogador?nomeJogador=${encodeURIComponent(valor)}&relatorio=${gerarRelatorio}`;
                break;
            case "temporada":
                url = `${apiUrl}/filtrarTemporada?nTemporada=${encodeURIComponent(valor)}&relatorio=${gerarRelatorio}`;
                break;
            case "jogador-ano":
                if (valor && valor2) {
                    url = `${apiUrl}/filtrarJogadorAno?nomeJogador=${encodeURIComponent(valor)}&nTemporada=${encodeURIComponent(valor2)}&relatorio=${gerarRelatorio}`;
                } else {
                    console.error("Para o filtro 'Nome do Jogador e Ano', insira ambos os valores.");
                    return;
                }
                break;
            case "jogadores-time":
                if (valor && valor2) {
                    url = `${apiUrl}/filtrarJogadoresTime?siglaTime=${encodeURIComponent(valor)}&nTemporada=${encodeURIComponent(valor2)}&relatorio=${gerarRelatorio}`;
                } else {
                    console.error("Para o filtro 'Jogadores do Time', insira ambos os valores.");
                    return;
                }
                break;
            default:
                console.error("Filtro desconhecido.");
                return;
        }

        if (url) {
            filtrarDados(url, gerarRelatorio);
        } else {
            console.error("Preencha pelo menos um campo para filtrar.");
        }
    });
});
