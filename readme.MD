# Database Scheme 

<img width="650" alt="Screenshot 2023-07-28 at 17 10 42" src="https://github.com/rizalherniawan/bvk-backend/assets/82139182/f0c970cb-28fa-4c70-8baf-1b030e434923">

## Description Relation
<ul>
<li>Transaction has relation of one to many to Carts</li>
<li>Items has relation of one to many to Carts</li>
<li>Users has relation of one to many to Carts</li>
</ul>

## Database Functionality
<ul>
<li>Transactions: Store all transactions historical data</li>
<li>Items: Store all datas of all items</li>
<li>Users: Store all users data, all users are treated as guest which identified by their email</li>
<li>Carts: Store all items that are going to be bought preceding to payment</li>
</ul>

