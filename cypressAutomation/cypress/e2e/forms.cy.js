/// <reference types="cypress" />

describe('Automate Radio buttons', () => {
    beforeEach(() => {
        cy.visit('http://www.rahulshettyacademy.com/AutomationPractice/');
    });


    it('checkboxes', () => {
        cy.get("#checkBoxOption1").should('exist').check()
        .should('be.checked').and('have.value', 'option1');
        cy.get('input[type="checkbox"]').as('checkboxes');
        if(cy.get('@checkboxes').should('have.length.gte', 3)) {
            cy.get('@checkboxes').check('option2').should('be.checked')
            cy.get('@checkboxes').check(['option3']);
        }
        
    });


    it('dropdown', () => {
        cy.get('select').select('option2').should('have.value', 'option2');
        cy.get('select').select('option3').should('have.value', 'option3');
        cy.get('select').select('option1').should('have.value', 'option1');
    });


    it("dyanmic dropdown", () => {
        const country = 'Cyprus';
        cy.get('#autocomplete').type('US');
        cy.get('.ui-menu-item').each(($el) => {
            let text = $el.text();
            cy.log(text);
            if(text === country) {
                $el.trigger('click'); // This doesn't break the loop
                return false; // This breaks the each loop
            }
        })
        cy.get('#autocomplete').should('have.value', country);
    });


    it('hidden / visible elements', () => {
        cy.get('#displayed-text').as('displayedText');
        cy.get('@displayedText').scrollIntoView();
        cy.get('@displayedText').should('be.visible');
        cy.get('#hide-textbox').click();
        cy.get('@displayedText').should('not.be.visible');
        cy.get('#show-textbox').click();
        cy.get('@displayedText').should('be.visible');
    });

    it('radio buttons', () => {
        cy.get('input[value="radio2"]').should('exist').check().should('be.checked');
        cy.get('input[value="radio1"]').should('exist').check().should('be.checked');
        cy.get('input[value="radio3"]').should('exist').check().should('be.checked');
    });

    it('alerts', () => {
        cy.get('#alertbtn').should('exist').click();
        cy.on('window:alert', (str) => {
            expect(str).to.equal('Hello , share this practice page and share your knowledge');
        });
        cy.get('#confirmbtn').should('exist').click();
        cy.on('window:confirm', (str) => {
            expect(str).to.equal('Hello , Are you sure you want to confirm?');
        });
    });
    
    it('open new tab', () => {
        
    });

})