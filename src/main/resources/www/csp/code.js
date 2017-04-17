function initGraph(elesJson){ // on dom ready
    if(cy)
        cy.destroy();


    var cy = window.cy = cytoscape({
        container: document.getElementById('cy'),

        style: cytoscape.stylesheet()
            .selector('node')
            .css({
                'background-color': '#B3767E',
                'shape': 'rectangle',
                'width': 50,
                'height': 50,
                'content': 'data(id)',
                'text-valign': 'center',
                'text-halign': 'center',
                'color': 'white',
                'font-weight': 'bold'
            })
            .selector('edge')
            .css({
                'line-color': '#F2B1BA',
                'target-arrow-color': '#B1C1F2',
                'target-arrow-shape': 'triangle',
                'opacity': 0.8,
                'content': 'data(id)',
                'edge-text-rotation': 'autorotate'
            })
            .selector(':selected')
            .css({
                'background-color': 'black',
                'line-color': 'black',
                'target-arrow-color': 'black',
                'source-arrow-color': 'black',
                'opacity': 1
            }),

        elements: elesJson,

        layout: {
            name: 'cose-bilkent',
            animate: false,
            padding: 25,
            idealEdgeLength: 250

        },

        ready: function(){
            // ready 2
            this.fit();
        }
    });

}