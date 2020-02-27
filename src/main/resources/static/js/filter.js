const API_URL = 'http://localhost:8080';
const $imageAvailableBox = $('#image-available-box');
const $pageSizeSelect = $('#page-size-select');
const $pageNumField = $('#page-number');
const $pageSortFieldSelect = $('#sort-field-select');
const $minPriceInput = $('#min-price-input');
const $maxPriceInput =$('#max-price-input');
const $prevPageBtn = $('#prev-btn');
const $nextPageBtn = $('#next-btn');
const $searchInput = $('#search-input');
let totalPages;
let subcategoriesId = [];
let searchAfterMain;

$(document).ready(() => {
    let url = new URL(window.location.href);
    searchAfterMain = url.searchParams.get('search');
    $searchInput.val(searchAfterMain ? `${searchAfterMain}` : '');
    $.ajax({
        url: `${API_URL}/category`,
        type: 'get',
        success: function (response) {
            // console.log(response);
            for (const category of response) {
                $('#category-filter-select').append(`
                  <option value="${category.id}" name="${category.name}">${category.name}</option>
                   `);
            }
        }
    });
    document.getElementById('min-price-input').value = 0;
    document.getElementById('max-price-input').value = 50000;
    makeFiltrationRequest();
});

document.getElementById('min-price-input').addEventListener('mousemove', () => {
    document.getElementById('test-min').value = document.getElementById('min-price-input').value;
    document.getElementById('max-price-input').setAttribute('min', `${document.getElementById('min-price-input').value}`);
});

document.getElementById('max-price-input').addEventListener('mousemove', () => {
    document.getElementById('test-max').value = document.getElementById('max-price-input').value;
    document.getElementById('min-price-input').setAttribute('max', `${document.getElementById('max-price-input').value}`);
});



let appendPublicationsToContainer = (publication) =>{
    // console.log(publication.imageName);
    let link = publication.imageName ? `http://localhost:8080/image/${publication.imageName}` : "https://novi-vorota.com.ua/catalog/view/theme/novivorota/images/notfound.png";
    $('.content').append(`
        <div class="item-catalog-list">
            <div class="image-container-catalog-list to-single-page">
                <img class="main-image" src="${link}" alt="">
            </div>
            <div class="publication-info-catalog-list">
            <div class="publication-main-info-catalog-list">
                <div class="publication-name-catalog-list">
                     <a href="publication.html?id=${publication.id}" target="_blank">${publication.name}</a>
                    <div class="publication-category-catalog-list">${publication.category}/${publication.subcategory}</div>
                </div>
                <div class="publication-description-catalog-list">
                    ${publication.description}
                </div>
                <div class="publication-price-catalog-list">
                    ${publication.price} $
                    <div class="publication-type-catalog-list">
                    ${publication.type}
                    </div>
                </div>
            </div>
            <div class="publication-delivery-catalog-list">
            <div class="delivery-info">${publication.delivery}
            <div class="publication-id-catalog-list">Publication id: ${publication.id}
            </div>
            </div>
            <div class="user-info">
            Seller: <a href="user-page.html?userId=${publication.userId}">${publication.username}</a>
            </div>
            </div>
            </div>
            </div>`)
};


function makeFiltrationRequest() {
    $('.content').html('');
    let pageSize = $pageSizeSelect.val();
    let pageNum = +$pageNumField.html() - 1;
    let pageField = $pageSortFieldSelect.val();
    let pageDirectionBtn  = $('.direction-btn:checked');
    let minPrice = $minPriceInput.val();
    let maxPrice = $maxPriceInput.val();
    let imageAv = $imageAvailableBox[0].checked;
    let search = $searchInput.val();
    let pageUrl = `paginationRequest.direction=${pageDirectionBtn.val()}&paginationRequest.field=${pageField}&paginationRequest.page=${pageNum}&paginationRequest.size=${pageSize}`;
    const subcategoriesId = [];
    $('.subcategory-filter').each((i, e) => {
        if(e.checked){
            subcategoriesId.push(e.value);
        }
    });
    let categoryId;
    if(subcategoriesId.length === 0 && categorySelect.val() != 0){
        categoryId = categorySelect.val();
    }
    let subcategoriesIdStr = subcategoriesId.join(',');
    let subUrl = subcategoriesIdStr ?'&subcategoryId=' + subcategoriesIdStr : '';
    $.ajax({
        url: `${API_URL}/publication/filter?${pageUrl}${subUrl ? subUrl : ''}${search ? '&name=' + search : ''}&maxPrice=${maxPrice}&minPrice=${minPrice}${imageAv ? '&image=' + imageAv : ''}${categoryId ? `&categoryId=${categoryId}` : ''}`,
        type: 'get',
        success: function (response) {
            for(let publication of response.data){
                appendPublicationsToContainer(publication.response);
            }
            // actionsOnNameClick();
            totalPages = response.totalPages;
            console.log(response);
        }
        // error: console.log(`${API_URL}publication/filter?${pageUrl}${subUrl ? subUrl : ''}&maxPrice=${maxPrice}}&minPrice=${minPrice}${imageAv ? '&image=' + imageAv : ''}`)
    });
}

let categorySelect = $('#category-filter-select');
let SFC = $('#subcategories-filter-container');
categorySelect.change(() => {
    subcategoriesId = [];
    let id = categorySelect.val();
    let name = $('#category-filter-select option:selected').text();
    // console.log(name);
    SFC.html('');
    $.ajax({
        url: `${API_URL}/subcategory/category?id=` + id,
        type: 'get',
        success: function (response) {
            SFC.append(`<label>Subcategories</label>`);
            for (const subcategory of response) {
                SFC.append(`
                        <div>
                            <label>
                                <input type="checkbox" class="subcategory-filter" value="${subcategory.id}">${subcategory.name}
                            </label>
                        </div>`);
            }

        }
    });
});

$('#filter').click(() => {
    makeFiltrationRequest();
});

$searchInput.change(() => {
    makeFiltrationRequest();
})

$prevPageBtn.click(() => {
    if(+$pageNumField.html() > 1){
        $pageNumField.html(+$pageNumField.html() - 1);
        makeFiltrationRequest();
    }
});

$nextPageBtn.click(() => {
    if(+$pageNumField.html() < totalPages){
        $pageNumField.html(+$pageNumField.html() + 1);
        makeFiltrationRequest();
    }
});

$pageSizeSelect.change(() => {
    $pageNumField.html('1');
    makeFiltrationRequest();
});

$pageSortFieldSelect.change(() => {
    makeFiltrationRequest();
});

