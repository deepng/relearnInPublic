/// <reference types="cypress" />
import _isEmpty from 'lodash/isEmpty';
import HomePage from '../PageObjects/homePage.cy';

describe('Advanced Tests', function() {
    before(function() {
        cy.fixture('more').then(function (data) {
            this.moreData = data;
            cy.visit(this.moreData.testSite1);
        });
        this.homePage = new HomePage();

    });

    it.skip('Angualar reference', function () {
        let name = cy.get('input[name="name"]:nth-child(2)');
        name.should('exist').type(this.moreData.name);
        name.should('have.attr', 'minlength', '2');
        cy.get('input[name="name"]:nth-child(1)').should('have.value', this.moreData.name);
        cy.get('select.form-control').select('Female');
        let radio = this.homePage.getRadioButton();
        radio.each(($el, index, $list) => {
            // Cypress commands like .should() and .expect() are asynchronous and don't return values for if conditions.
            // Instead, use jQuery methods or DOM properties for conditional logic.
            const $input = Cypress.$($el); // get raw DOM element as jQuery object
            if ($input.attr('type') === 'radio') {
                // figure out how to find the child for an element in cypress
                cy.log($input.next('.form-check-label').val());
                cy.log('Radio button found -> ') + $el.next('label'); 
                const label = '' //radio.eq(index).find('label').text();
                if(_isEmpty(label)) {
                    cy.log('Label is empty');
                } else {
                    cy.log(label);
                    if (label.includes(this.moreData.disabledRadio)) {
                        cy.wrap($input).should('be.disabled');
                        cy.log('Radio button $input is disabled');
                        return false; // break the loop
                    }
                }
            }
        });
    });


    it.only('Shop items', function() {
        this.homePage.getShop().click();
        cy.url().should('include', this.moreData.testSite2);
        // cy.get('#navbarResponsive').should('exist').click();
        // cy.get('#navbarResponsive').should('have.attr', 'class').and('include', 'show');
        // cy.get('#navbarResponsive').click(); // close the menu
        cy.selectProduct('Blackberry');
        cy.get('a.nav-link.btn.btn-primary').contains('Checkout').click();
    });



});


