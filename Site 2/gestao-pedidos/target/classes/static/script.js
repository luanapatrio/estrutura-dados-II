const API_URL = '/api/pedidos';
let tabelaDataTable;

function formatarMoeda(valor) {
  return 'R$ ' + parseFloat(valor).toFixed(2).replace('.', ',');
}

function atualizarTabela() {
  fetch(API_URL)
    .then(response => response.json())
    .then(pedidos => {
      if (tabelaDataTable) {
        tabelaDataTable.destroy();
      }

      const corpoTabela = document.getElementById('corpoTabela');
      corpoTabela.innerHTML = '';

      let totalGeral = 0;

      pedidos.forEach(p => {
        const totalItem = p.quantidade * p.valorUnitario;
        totalGeral += totalItem;

        const linha = document.createElement('tr');
        linha.innerHTML = `
          <td>${p.id}</td>
          <td>${p.item}</td>
          <td>${p.descricao || ''}</td> <!-- NOVA COLUNA -->
          <td>${p.quantidade}</td>
          <td>${formatarMoeda(p.valorUnitario)}</td>
          <td>${formatarMoeda(totalItem)}</td>
          <td>
            <button class="btn btn-warning btn-sm me-1" onclick="prepararEdicao(${p.id})">
              <i class="bi bi-pencil-square"></i>
            </button>
            <button class="btn btn-danger btn-sm" onclick="excluirItem(${p.id})">
              <i class="bi bi-trash"></i>
            </button>
          </td>
        `;
        corpoTabela.appendChild(linha);
      });

      document.getElementById('totalGeral').innerText = formatarMoeda(totalGeral);

      tabelaDataTable = $('#tabelaPedido').DataTable({
        language: { url: 'https://cdn.datatables.net/plug-ins/1.13.6/i18n/pt-BR.json' },
        order: [[0, 'asc']]
      });
    });
}

document.getElementById('formAdicionar').addEventListener('submit', function (e) {
  e.preventDefault();

  const novoItem = {
    item: document.getElementById('addNome').value,
    descricao: document.getElementById('addDescricao').value, // NOVO CAMPO
    quantidade: parseInt(document.getElementById('addQuantidade').value),
    valorUnitario: parseFloat(document.getElementById('addValor').value)
  };

  fetch(API_URL, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(novoItem)
  })
    .then(() => {
      atualizarTabela();
      bootstrap.Modal.getInstance(document.getElementById('modalAdicionar')).hide();
      this.reset();
    });
});

function prepararEdicao(id) {
  fetch(`${API_URL}/${id}`)
    .then(response => response.json())
    .then(itemEncontrado => {
      document.getElementById('editId').value = itemEncontrado.id;
      document.getElementById('editNome').value = itemEncontrado.item;
      document.getElementById('editDescricao').value
        = itemEncontrado.descricao || ''; // NOVO CAMPO
      document.getElementById('editQuantidade').value = itemEncontrado.quantidade;
      document.getElementById('editValor').value = itemEncontrado.valorUnitario;

      const modal = bootstrap.Modal.getOrCreateInstance(document.getElementById('modalEditar'));
      modal.show();
    });
}

document.getElementById('formEditar').addEventListener('submit', function (e) {
  e.preventDefault();

  const id = document.getElementById('editId').value;
  const itemAtualizado = {
    item: document.getElementById('editNome').value,
    descricao: document.getElementById('editDescricao').value, // NOVO CAMPO
    quantidade: parseInt(document.getElementById('editQuantidade').value),
    valorUnitario: parseFloat(document.getElementById('editValor').value)
  };

  fetch(`${API_URL}/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(itemAtualizado)
  })
    .then(() => {
      atualizarTabela();
      bootstrap.Modal.getInstance(document.getElementById('modalEditar')).hide();
    });
});

function excluirItem(id) {
  if (confirm('Tem certeza que deseja excluir este item?')) {
    fetch(`${API_URL}/${id}`, { method: 'DELETE' })
      .then(() => atualizarTabela());
  }
}

document.addEventListener('DOMContentLoaded', function () {
  atualizarTabela();
});