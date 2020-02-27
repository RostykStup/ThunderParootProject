
let pageSize = $('.page-size');
let pageNum = $('#page-number');
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

pageSize.change(function () {
    // location.reload();
    $('.container').html('');
    let size = pageSize.val();
    let pages = pageNum.html();

    console.log(pages);
    console.log(size);
    $.ajax({
        url: `http://localhost:8080/publication/?page=${pages}&size=${size}&field=name&direction=DESC`,
        type: 'get',
        success: function (response) {
            console.log('success', response);
            for(let publication of response.data){
                appendPublicationsToContainer(publication);
            }
        },
        error: console.log('error')
    });

});


let getPublications = () =>{
    let size = pageSize.val();
    let pages = pageNum.html();

    $.ajax({
        url: `http://localhost:8080/publication/?page=${pages}&size=${size}&field=name&direction=DESC`,
        // contentType: 'application/json',
        type: 'get',
        // data: JSON.stringify(request),
        success: function (response) {
                // console.log('success', response);
                for(let publication of response.data){
                    appendPublicationsToContainer(publication);
                }
        },
        error: console.log('error')
    });
};