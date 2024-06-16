document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("btn-filtrar-jogador").addEventListener("click", function () {
        const nomeJogador = document.getElementById("input-nome-jogador").value;
        fetch(`/api/filtrarJogador?nomeJogador=${nomeJogador}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao filtrar jogador.');
                }
                return response.json();
            })
            .then(data => {
                const jogadorList = document.getElementById("jogador-list");
                jogadorList.innerHTML = "";
                data.forEach(jogador => {
                    const listItem = document.createElement("li");
                    listItem.textContent = `ID: ${jogador.id}, Nome: ${jogador.nome}, Idade: ${jogador.idade}, Minutos Jogados: ${jogador.minutosJogados} min, Total Pontos: ${jogador.pontos}, Time: ${jogador.time}, Temporada: ${jogador.temporada}`;
                    jogadorList.appendChild(listItem);
                });
            })
            .catch(error => console.error("Erro ao filtrar jogador:", error));
    });

    document.getElementById("btn-filtrar-temporada").addEventListener("click", function () {
        const temporada = document.getElementById("input-n-temporada").value;
        fetch(`/api/filtrarTemporada?nTemporada=${temporada}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao filtrar temporada.');
                }
                return response.json();
            })
            .then(data => {
                const temporadaList = document.getElementById("temporada-list");
                temporadaList.innerHTML = "";
                data.forEach(jogador => {
                    const listItem = document.createElement("li");
                    listItem.textContent = `ID: ${jogador.id}, Nome: ${jogador.nome}, Idade: ${jogador.idade}, Minutos Jogados: ${jogador.minutosJogados} min, Total Pontos: ${jogador.pontos}, Time: ${jogador.time}, Temporada: ${jogador.temporada}`;
                    temporadaList.appendChild(listItem);
                });
            })
            .catch(error => console.error("Erro ao filtrar temporada:", error));
    });

    document.getElementById("btn-filtrar-jogador-ano").addEventListener("click", function () {
        const nomeJogador = document.getElementById("input-nome-jogador-ano").value;
        const temporada = document.getElementById("input-n-temporada-jogador-ano").value;
        fetch(`/api/filtrarJogadorAno?nomeJogador=${nomeJogador}&nTemporada=${temporada}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao filtrar jogador para o ano.');
                }
                return response.json();
            })
            .then(data => {
                const jogadorAnoList = document.getElementById("jogador-ano-list");
                jogadorAnoList.innerHTML = "";
                data.forEach(jogador => {
                    const listItem = document.createElement("li");
                    listItem.textContent = `ID: ${jogador.id}, Nome: ${jogador.nome}, Idade: ${jogador.idade}, Minutos Jogados: ${jogador.minutosJogados} min, Total Pontos: ${jogador.pontos}, Time: ${jogador.time}, Temporada: ${jogador.temporada}`;
                    jogadorAnoList.appendChild(listItem);
                });
            })
            .catch(error => console.error("Erro ao filtrar jogador para o ano:", error));
    });

    document.getElementById("btn-filtrar-time").addEventListener("click", function () {
        const temporada = document.getElementById("input-n-temporada-time").value;
        fetch(`/api/filtrarTime?nTemporada=${temporada}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao filtrar times.');
                }
                return response.json();
            })
            .then(data => {
                const timesList = document.getElementById("time-list");
                timesList.innerHTML = "";
                data.forEach(time => {
                    const listItem = document.createElement("li");
                    listItem.textContent = time;
                    timesList.appendChild(listItem);
                });
            })
            .catch(error => console.error("Erro ao filtrar times:", error));
    });

    document.getElementById("btn-filtrar-jogadores-time").addEventListener("click", function () {
        const temporada = document.getElementById("input-n-temporada-jogadores-time").value;
        const siglaTime = document.getElementById("input-sigla-time").value;
        fetch(`/api/filtrarJogadoresTime?nTemporada=${temporada}&siglaTime=${siglaTime}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao filtrar jogadores do time.');
                }
                return response.json();
            })
            .then(data => {
                const jogadoresTimeList = document.getElementById("jogadores-time-list");
                jogadoresTimeList.innerHTML = "";
                data.forEach(jogador => {
                    const listItem = document.createElement("li");
                    listItem.textContent = `Time: ${jogador.time}, ID: ${jogador.id}, Nome: ${jogador.nome}`;
                    jogadoresTimeList.appendChild(listItem);
                });
            })
            .catch(error => console.error("Erro ao filtrar jogadores do time:", error));
    });
});

