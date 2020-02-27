// const API_URL = 'http://localhost:8080';
const $categoriesNavCol = $('#categories-navigation');
const $logOutBtn = $('#log-out-btn');
const $logInBtn = $('#nav-login-btn');
const $navCartHref = $('#nav-cart-href');
const $searchUl = $('#search-ul');

// const $searchInput = $('#search-input');
// http://localhost:8080/cart/getCart



$('#search-input').change( () => {
    window.location.href = `filter-page.html?search=${$('#search-input').val()}`;
});

// $('#search-input').keypress(() => {
//     let search = $('#search-input').val();
//     $.ajax({
//             url: `http://localhost:8080/publication/searching?paginationRequest.direction=ASC&paginationRequest.field=name&paginationRequest.page=0&paginationRequest.size=10&search=${search}`,
//         contentType: 'application/json',
//         headers: {
//             "Authorization": `Bearer ${window.localStorage.getItem('userToken')}`
//         },
//         type: 'get',
//         success: function (response) {
//             $searchUl.html('');
//             appendResultsToSearch(response.data);
//         }
//
// //     });
// // // });
// //
// //  function appendResultsToSearch(results){
// //      for (let result of results){
// //          $searchUl.append(`
// //                 <li class="sub-navigation-item search">
// //                     <a class="sub-navigation-link search" href="filter-page.html?search=${result}">${result}</a>
// //                 </li>
// //          `);
// //      }
// //  }

$(document).ready(() => {
    $.ajax({
        url: `http://localhost:8080/category`,
        type: 'get',
        success: function (response) {
            appendCategoriesToNavigation(response);
        }
    });
    $('#user-profile-info')[0].innerHTML = window.localStorage.getItem('username') ? window.localStorage.getItem('username') : 'Log in';
    $.ajax({
        url: `http://localhost:8080/cart/getCart`,
        contentType: 'application/json',
        headers: {
            "Authorization": `Bearer ${window.localStorage.getItem('userToken')}`
        },
        type: 'get',
        // data: JSON.stringify(request),
        success: function (response) {
            // console.log('ok');
            $navCartHref.html($navCartHref.html() + ' (' + response.elements + ')');
        }

    });
});

function appendCategoriesToNavigation(categories) {
    for(let category of categories){
        $categoriesNavCol.append(`
                <li class="sub-navigation-item  has-sub-nav">
                    <a class="sub-navigation-link" href="#">${category.name}</a>

                    <ul class="sub-navigation subcategory-nav nav--${category.id}">
<!--                    <input type="text" value="${category.id}" style="display: none" class="has-cat-id">-->
                    </ul>
                    </li>
            `);
        $.ajax({
            url: `http://localhost:8080/subcategory/category?id=${category.id}`,
            type: 'get',
            success: function (response) {
                for (let subcategory of response){
                    $(`.nav--${category.id}`).append(`
                        <li class="sub-navigation-item">
                            <a class="sub-navigation-link" href="#">${subcategory.name}</a>
                        </li>
                       `);
                }
            }
        });
    }
}

$logOutBtn.click(() => {
    console.log('log out');
    window.localStorage.setItem('userToken', '');
    window.localStorage.setItem('username', '');
    window.localStorage.setItem('userId', '');
    window.location.href= 'main-page.html';
});

$('#user-profile-info').click(() => {
    console.log('aaa');
   if (!window.localStorage.getItem('username')){
       window.location.href = 'login.html';
   }
});


