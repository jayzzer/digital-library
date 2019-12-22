new autoComplete({
    data: {
        src: async () => {
            const query = document.querySelector('#autoComplete1').value;
            const source = await fetch(`http://localhost:8081/api/search?text=${query}`);
            const data = await source.json();
            return data;
        },
        key: ["title", "author", "genre"],
        cache: false
    },
    searchEngine: 'loose',
    debounce: 200,
    highlight: true,
    threshold: 2,
    maxResults: 5,
    selector: '#autoComplete1',
    resultsList: {
        render: true,
        container: source => {
            source.setAttribute('id', 'book_list');
        },
        destination: document.querySelector('#autoComplete1'),
        position: 'afterend',
        element: 'ul'
    },
    resultItem: {
        content: (data, source) => {
            source.innerHTML = data.match;
        },
        element: "li"
    },
    onSelection: feedback => {
        switch (feedback.selection.key) {
            case "title":
                location.replace(`/books/search?text=${feedback.selection.value.title}`);

                break;
            case "author":
                location.replace(`/books/search?text=${feedback.selection.value.author}`);

                break;
            case "genre":
                location.replace(`/books/search?text=${feedback.selection.value.genre}`);

                break;
        }
    }
});