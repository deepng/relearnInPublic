/// <reference types="cypress" />
import 'cypress-iframe';

const website = 'http://www.rahulshettyacademy.com/AutomationPractice/';

describe('various actions', () => {
    before(() => {
        cy.visit(website);
    });
    
    it('mouse hover', () => {
        cy.get('div.mouse-hover-content').scrollIntoView().invoke('show');
        cy.wait(5000);
        cy.contains('Top').click({force: true});
        cy.url().should('include', 'top');
    });

    it.skip('Grab attributes from an element', () => {
        cy.get('a#opentab').should('exist').then((el) => { // We need to resolve the promise ourselves since its using prop() jquery method
            let url = el.prop('href');
            cy.log(url);
            expect(url).to.contain('qaclickacademy');
            cy.origin(url, () => {
                cy.visit(url);
            });
            cy.origin(website, () => {
                cy.go('back');
            });
        });
    });


    it.only('Switching to frames', () => {
        // cy.origin('http://www.rahulshettyacademy.com', () => {
            cy.frameLoaded('#courses-iframe');  // Load the iframe
            cy.iframe().find('a[href="mentorship"]').eq(0).should('exist').click();
            cy.iframe().find('h1[class="pricing-title text-center"]').should('have.length', 2);
        // });
    })

    after(() => {
        cy.log('Test completed');
    });

});