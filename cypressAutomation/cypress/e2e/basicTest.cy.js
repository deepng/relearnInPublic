/// <reference types="cypress" />

describe('Basic Bofore Test', () => {
  before(function () {
    cy.fixture('users').then((user) => {
      this.user = user;
    });
  });

  it('login to vymo', function () {
    cy.visit(this.user.stagingUrl).wait(6000)
    cy.get('#field-username').should('exist').type(this.user.validUserId);
    cy.get('button[data-test-id="login-button"]').should('exist').click();
    cy.get('#field-password').should('exist').type(this.user.validUserPassword);
    cy.get('button[data-test-id="password-button"]').should('exist').click();
    cy.url().should('include', '/dashboard');
    cy.get('.ant-menu').find('.ant-menu-submenu').as('menu');
    cy.get('@menu').should('have.length.gt', 4);
    cy.get('@menu').each(($el) => { // We want to run through all these elements
      let role = $el.attr('role');
      if(role !== 'menuitem') { // is not a menu item
          let x = $el.find('div').attr('role');
          assert.equal('menuitem', x, 'The div insite the list view should be a menuItem'); // should be a menu item
      } else {
        cy.log('This is a menu item');
      }
    });
  })
});