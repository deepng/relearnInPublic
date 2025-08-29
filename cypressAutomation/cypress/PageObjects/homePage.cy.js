/// <reference types ="cypress" />


class HomePage {

    getNameInput() {
        return cy.get('input[name="name"]:nth-child(2)');
    }

    getRadioButtons() {
        return cy.get('input.form-check-input');
    }

    getShop() {
        return cy.get('a[href*="shop"]');
    }

}

export default HomePage;