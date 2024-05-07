<script>
    $(document).ready(function () {
        $('button[data-action="pausar"]').click(function () {
            var idExterno = $(this).data('id');
            // Faça a requisição PUT usando jQuery AJAX
            $.ajax({
                url: '/task-manager/pausar/' + idExterno, // Substitua 'sua-url-aqui' pela URL correta
                type: 'PUT',
                success: function () {
                    // Recarregue a página após a requisição PUT ser concluída
                    location.reload();
                },
                error: function () {
                    alert('Erro ao finalizar a tarefa.');
                }
            })
        })
    })
</script>