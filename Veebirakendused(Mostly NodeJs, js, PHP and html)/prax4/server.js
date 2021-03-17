const express = require('express')
const app = express()
const bodyParser = require('body-parser')
const port = 6058
const path = require('path')
let counter = 0;
let gameCounter = 0;
let searchingPeople = []
let games = []

app.use(express.static(path.join(__dirname, '../prax4')))
app.use(bodyParser.urlencoded({ extended: true }))
app.use(bodyParser.json())
app.engine('html', require('ejs').renderFile);
app.set('view engine', 'html');
app.set('views', __dirname);

app.post('/newTurn', (req, res) => {
    let game = null;
    games.forEach((i) => {
        if (parseInt(i.id) === parseInt(req.body.gId)) {
            game = i
        }
    })
    if (game != null) {
        games.splice(games.indexOf(game), 1)
        let updateGame = {
            id: game.id,
            player1: game.player1,
            player2: game.player2,
            turns: game.turns,
            dice: "empty",
            lockIn: "empty"
        }
        games.push(updateGame)
        res.send("Done")
    } else {
        res.send("Couldnt lockIn")
    }
})

app.get('/getDice/:gId', (req, res) => {
    let game = null;
    games.forEach((i) => {
        if (parseInt(i.id) === parseInt(req.params.gId)) {
            game = i
        }
    })
    let update;
    if (game !== null && game.dice !== "empty") {
        update = {
            dice: game.dice,
            lockIn: game.lockIn,
            checked: game.checkedDice
        }
        res.send(update)
    } else {
        update = {
            dice: "null",
            lockIn: "empty"
        }
        res.send(update)
    }
})

app.post('/dice', (req, res) => {
    let game = null;
    games.forEach((i) => {
        if (parseInt(i.id) === parseInt(req.body.gId)) {
            game = i
        }
    })
    if (game != null) {
        games.splice(games.indexOf(game), 1)
        let updateGame = {
            id: game.id,
            player1: game.player1,
            player2: game.player2,
            turns: game.turns,
            dice: req.body.dices,
            checkedDice: req.body.checked,
            lockIn: game.lockIn
        }
        games.push(updateGame)
        res.send("Done")
    } else {
        res.send("Couldnt be done")
    }
})

app.get('/gameInfo/:pId/:gId', (req, res) => {
    let game = null;
    games.forEach((i) => {
        if (parseInt(i.id) === parseInt(req.params.gId)) {
            game = i
        }
    })
    if (game !== null) {
        res.send({
            player1: game.player1,
            player2: game.player2
        })
    }
})

app.get('/game/:id', (req, res) => {
    let gameId = null;
    let rounds = null;
    let opponentId = null;
    games.forEach((i) => {
        if (parseInt(i.player1.id) === parseInt(req.params.id)
            || parseInt(i.player2.id) === parseInt(req.params.id)) {
            gameId = i.id;
            rounds = i.turns;
            if (parseInt(i.player1.id) === parseInt(req.params.id)) {
                opponentId = i.player2.id
            } else {
                opponentId = i.player1.id
            }
        }
    })
    if (gameId !== null && rounds !== null) {
        res.send({found: true, gameUrl: '../public/game.html?gId=' + gameId + '&pId='
                + req.params.id + '&oId=' + opponentId + '&turns=' + rounds})
    } else (
        res.send({found: false})
    )
})

app.post('/wait', (req, res) => {
    console.log(searchingPeople.length)
    console.log(req.body)
    let index = 0;
    let foundPlayer = null;
    if (searchingPeople.length > 0) {
        searchingPeople.forEach((i) => {
            if (foundPlayer === null && req.body.turns === i.turns) {
                foundPlayer = i
                let newGame = {
                    id: gameCounter,
                    player1: {id: counter, name: req.body.name},
                    player2: {id: i.id, name: i.name},
                    turns: req.body.turns,
                    dice: "empty",
                    lockIn: "empty"
                    }
                games.push(newGame)
            }
        })
    }
    if (foundPlayer != null) {
        searchingPeople.splice(searchingPeople.indexOf(foundPlayer), 1)
        res.redirect('public/game.html?gId=' + gameCounter + '&pId=' +  counter + '&oId='
            + foundPlayer.id + '&turns=' + req.body.turns)
        counter++;
        gameCounter++;
    } else {
        searchingPeople[counter] = {
            id: counter,
            name: req.body.name,
            turns: req.body.turns,
            response: res,
            ingame: false,
        }
        index = counter;
        counter = counter + 1
    }
    if (typeof searchingPeople[index] != 'undefined' && searchingPeople[index].ingame === false) {
        res.redirect('../public/wait.html?&pId=' + index);
    }
})

app.get('/next', (req, res) =>{
    res.send('Ouyee')
})

app.listen(port, () => {
    console.log(`listening at: ${port}`)
})
