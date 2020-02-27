
let pageSize = $('.page-size').val();
let pageNum = $('#page-number').html();
let container = $('.container');

let appendPublicationsToContainer = (publication) =>{
    let link = `http://localhost:8080/test/${publication.imageName}`;
    container.append(`
   <div class="item-catalog-list">
   <div class="image-container-catalog-list" style="background: url(${link}) center no-repeat;background-size: cover">
        </div>
        <h4>${publication.name}</h4>
        <p>${publication.price}</p>
        <p> ${publication.description}</p>
    </div>`)
};

let request = {
    page: pageNum,
    size: pageSize,
    field: 'name',
    direction: 'ASC'
};

let getPublications = () =>{
    console.log(pageNum);
    console.log(pageSize);
    $.ajax({
        url: 'http://localhost:8080/publication',
        // contentType: 'application/json',
        type: 'get',
        // data: JSON.stringify(request),
        success: function (response) {
            console.log('success', response);
            for(let publication of response){
                appendPublicationsToContainer(publication);
            }
        },
        error: console.log('error')
    });
};