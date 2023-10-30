
Feature: customer make order

Scenario: customer make order successfully
    Given  customer want to make an order
    When  customer with name 'nour@gmail.com' and customer id '2'
    And make order with product name 'zamor' and product id '3'and product price '40'
    And  make order with quantity '2'
    Then  make order successfully
    
    
    Scenario: customer update order successfully
    Given  customer want to make an order
     When  customer with name 'nour@gmail.com' and customer id '2'
    And make order with product name 'zamor' and product id '3'and product price '40'
    And  set order with quantity '3'
    Then  update order successfully
    
    
 Scenario: customer delete order successfully
    Given  customer want to delete an order
    When  customer name 'nour@gmail.com' and customer id '2'
    And  order with product name 'zamor' and product id '3'and product price '40'
    And   order with quantity '3'
   Then  delete order successfully
    
 