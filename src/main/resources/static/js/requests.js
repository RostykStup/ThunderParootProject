const $pageNumber = $('#page-number');
const $entities = $('#entities');
const $pageSize = $('#page-size-select');
const updateModal =$('#update-modal');
const creationModal = $('#create-modal');
const openCreateModalBtn = $('#open-create-modal-btn');
const createBtn = $('#create-btn');
const updateBtn = $('#update-btn');


let modal = $('.modal');
let cancelBtn = $('.cancel-btn');
// let modal = $('.modal');
let totalPages;

function actionsSimpleEntity(entity){
    firstLoad(entity);
    actionsOnIncrementBtn(entity);
    actionsOnDecrementBtn(entity);
    actionsOnCreate(entity);
    actionsOnUpdate(entity);
    selectPageSizeChange(entity);
}

function openCreateModal() {
    creationModal.css("display","block");
}

function openUpdateModal() {
    updateModal.css("display","block");
}

function closeModal() {
    modal.css("display","none");
}

function appendElementsToTable(element) {
    $('#entities').append(`
            <tr>
             <td class="entity-id-col">${element.id}</td>
             <td class="entity-name-col">${element.name}</td>
                 <td>
                  <button value="${element.id}" class="delete-btn">Delete
                  </button>
                 <button value="${element.id}" class="update-btn">Update
                 </button>
                  </td>
             </tr>
        `);
};

function makeRequest(entity) {
    let pageSize = $pageSize.val();
    let pageNum = $pageNumber.html();
    $.ajax({
        url: `http://localhost:8080/${entity}/?page=${pageNum-1}&size=${pageSize}&field=name&direction=ASC`,
        type: 'get',
        success: function (response) {
                for (let elements of response.data) {
                    appendElementsToTable(elements);
            }
            actionsOnDeleteButton(entity);
            actionsOnUpdateButton();
            totalPages = response.totalPages;
        }
    });
}

function actionsOnDeleteButton(entity) {
    $('.delete-btn').click((e) =>{
        let id = e.target.value;
        $.ajax({
            url: `http://localhost:8080/${entity}?id=`+id,
            type: 'delete',
            success: function () {
                console.log('deleted');
                location.reload();
                // redrawTable();
            }
        })
    });
};

openCreateModalBtn.click(() => {
    openCreateModal();
});

cancelBtn.click(() => {
    closeModal();

});
function actionsOnCreate(entity) {
    createBtn.click(() => {
        let request = {
            name: $('#create-entity-name').val()
        };
        $.ajax({
            url: `http://localhost:8080/${entity}`,
            contentType: 'application/json',
            type: 'post',
            data: JSON.stringify(request),
            success: function (response) {
                console.log('post', response);
                closeModal();
                $('#create-entity-name').val('');
                location.reload();
                // redrawTable();
            }
        });

    });
}

function actionsOnUpdate(entity) {
    updateBtn.click(() => {
        let id = $('#update-entity-id').val();
        console.log(id);
        let request = {
            name: $('#update-entity-name').val()
        }
        $.ajax({
            url: `http://localhost:8080/${entity}?id=` + id,
            contentType: 'application/json',
            type: 'put',
            data: JSON.stringify(request),
            success: function (response) {
                console.log('put', response);
                $('#update-entity-name').val('');
                $('#update-entity-id').val('');
                closeModal();
                location.reload();
                // redrawTable();
            }
        });
    });
}
function actionsOnUpdateButton() {
    $('.update-btn').click((e) =>{
        let id = e.target.value;
        let $btn = $(e.target);
        $('#update-entity-name').val($btn.parent().siblings('.entity-name-col').html());
        $('#update-entity-id').val(id);
        openUpdateModal();
    });
};


function firstLoad(entity){
    makeRequest(entity);
}



function actionsOnIncrementBtn(entity) {
    $('#increment-page-num-btn').click(() => {

        if(+$pageNumber.html() < totalPages) {
            $entities.html('');
            $pageNumber.html(`${+$pageNumber.html() + 1}`);
            makeRequest(entity);
        }
    })
}

function selectPageSizeChange(entity) {
    $pageSize.change(() => {
        $entities.html('');
        $pageNumber.html('1');
        makeRequest(entity);
    });
}

function actionsOnDecrementBtn(entity) {
    $('#decrement-page-num-btn').click(() => {
        if(+$pageNumber.html() > 1) {
            $entities.html('');
            $pageNumber.html(`${+$pageNumber.html() - 1}`);
            makeRequest(entity);
        }
    })
}