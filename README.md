# relearnInPublic

Trying to practice my skills as I seek to improve myself

do run "cypress open" to enable cypress to add the default configurations

add the tests

Cypress only uses css-selectors
Accessing elements using
id = #idNmae / tagName#className
class = .className / tagName.className
others = tagname[] eg:tagname[type='search']
Parents = parentTagName childTagName

Cypress being a node project is asynchronos in nature, but cypress uses .then() function to make it synchronos

In chai, comparison needs `have.` and behavioral needs `be.`
example: `(have.value, 4)` and `(be.checked)` these are chai notations that can be used in Cypress

Cypress always accepts the alerts

Target attr in HTML will specify where to open the linked document
<a href="www.google.com" target="_blank" class="linkclass"/>
target will indicate the DOM to open in new window

