const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const gameId = urlParams.get('gId');
const playerId = parseInt(urlParams.get('pId'))
const opponentId = parseInt(urlParams.get('oId'))
const roundCount = parseInt(urlParams.get('turns'));

let turnId, wait, player1, player2;
let currentRound = 1;
let opponentLocked = 0;
let myLocked = 0;
let counter = 0;
var isDiceRolled = false;
let rerolls = 3;
var facesTable = ["ones", "twos", "threes", "fours", "fives", "sixes"];
var specialTable = ["threekind", "fourkind", "fullhouse", "smallstraight", "largestraight", "chance", "yahtzee"];

initialize();
getOpponentMoves();

fetch('/gameInfo/' + playerId + '/' + gameId)
.then(data=>{return data.json()})
.then(res=>{
		document.getElementById("player1").innerHTML = res.player1.name;
		document.getElementById("player2").innerHTML = res.player2.name;
		if (playerId === player1) {
			document.getElementById("name").innerHTML = res.player1.name;
		} else {
			document.getElementById("name").innerHTML = res.player2.name;
		}
		initialize();
	})

function initialize() {
	if (playerId < opponentId) {
		turnId = playerId;
		wait = false;
		player1 = opponentId;
		player2 = playerId;
	} else {
		turnId = opponentId;
		wait = true;
		player1 = playerId;
		player2 = opponentId;
	}
	if (turnId === player1) {
		document.getElementById("turnId").innerHTML = document
			.getElementById("player" + 1).innerHTML + " turn!";
	} else {
		document.getElementById("turnId").innerHTML = document
			.getElementById("player" + 2).innerHTML + " turn!";
	}
	document.getElementById("roundsTotal").innerHTML = roundCount + " rounds total!";
	document.getElementById("currentRound").innerHTML = "Round number: 1";
	document.getElementById("rerolls").innerHTML = "Rolls remaing: 3";
}

function rollDice() {
	if (rerolls > 0 && turnId === playerId){
		let diceSum = 0;
		let dicesDto = [];
		let checkedDto = [];
		for (let i = 1; i <= 5; i++) {
			let num;
			let die = document.getElementById("die" + i);
			if (die.className === "unchecked") {
				num = Math.floor(Math.random() * 6) + 1;
				die.src = "images/dice_" + num + ".png";
				die.value = num;
				checkedDto.push(0);
			} else {
				num = die.value;
				checkedDto.push(1);
			}
			dicesDto.push(num);
			diceSum += num;
		}
		let data = {
			gId: gameId,
			dices: dicesDto,
			checked: checkedDto
		};
		fetch("/dice", {
			method: "POST",
			body: JSON.stringify(data),
			headers: {"Content-type": "application/json; charset=UTF-8"}
		}).then();
		document.getElementById("diceScore").innerHTML = "Dice score: " + diceSum;
		checkStatus();
		rerolls--;
		document.getElementById("rerolls").innerHTML = "Rolls remaining: " + rerolls.toString();
		isDiceRolled = true;
	}
}

function checkStatus() {
	let tablePos;
	let ones = 0;
	let twos = 0;
	let threes = 0;
	let fours = 0;
	let fives = 0;
	let sixes = 0;
	let onesCounter = 0;
	let twosCounter = 0;
	let threesCounter = 0;
	let foursCounter = 0;
	let fivesCounter = 0;
	let sixesCounter = 0;
	for (let i = 1; i <= 5; i++) {
		let num = document.getElementById("die" + i).value;
		if (num === 1) {
			ones += num;
			onesCounter++;
		} else if (num === 2) {
			twos += num;
			twosCounter++;
		} else if (num === 3) {
			threes += num;
			threesCounter++;
		} else if (num === 4) {
			fours += num;
			foursCounter++;
		} else if (num === 5) {
			fives += num;
			fivesCounter++;
		} else if (num === 6) {
			sixes += num;
			sixesCounter++;
		}
	}
	let counterList = [onesCounter, twosCounter, threesCounter, foursCounter, fivesCounter, sixesCounter];
	let list = [ones, twos, threes, fours, fives, sixes];
	let sum = list.reduce((a, b) => a + b, 0);
	for (let i = 0; i < facesTable.length; i++) {
		if (turnId === player1) {
			tablePos = document.getElementById('p1' + facesTable[i]);
		} else {
			tablePos = document.getElementById( 'p2' + facesTable[i]);
		}
		if (tablePos.className !== "locked") {
			tablePos.innerHTML = "0";
			let num = list[i];
			if (num !== 0) {
				tablePos.innerHTML = num;
			}
		}
	}
	checkSpecials(sum, counterList);
}


function checkSpecials(sum, counterList) {
	let turn = getTurn();
	for (let i = 0; i < specialTable.length; i++) {
		let tablePos = document.getElementById(turn + specialTable[i]);
		if (tablePos.className !== "locked") {
			tablePos.innerHTML = "0";
		}
	}
	let chancePos = document.getElementById(turn + "chance")
	if (chancePos.className !== "locked") {
		chancePos.innerHTML = sum;
	}
	for (let i = 0; i <= counterList.length; i++) {
		if (counterList[i] >= 3) {
			let threeKind = document.getElementById(turn + "threekind");
			if (threeKind.className !== "locked") {
				threeKind.innerHTML = sum;
			}
			if (counterList[i] >= 4) {
				let fourKindPos = document.getElementById(turn + "fourkind");
				if (fourKindPos.className !== "locked") {
					fourKindPos.innerHTML = sum;
				}
				if (counterList[i] === 5) {
					let yahtzeePos = document.getElementById(turn + "yahtzee");
					if (yahtzeePos.className !== "locked") {
						yahtzeePos.innerHTML = "50";
					} else {
						yahtzeePos.innerHTML = (parseInt(yahtzeePos.innerHTML) + 50).toString();
					}
				}
			}
		}
	}
	let onesCounted = counterList[0];
	let twosCounted = counterList[1];
	let threesCounted = counterList[2];
	let foursCounted = counterList[3];
	let fivesCounted = counterList[4];
	let sixesCounted = counterList[5];
	let smallPos = document.getElementById(turn + "smallstraight");
	if (smallPos.className !== "locked") {
		if (onesCounted >= 1 && twosCounted >= 1 && threesCounted >= 1 && foursCounted >= 1 ||
			twosCounted >= 1 && threesCounted >= 1 && foursCounted >= 1 && fivesCounted >= 1 ||
			threesCounted >= 1 && foursCounted >= 1 && fivesCounted >= 1 && sixesCounted >= 1) {
			smallPos.innerHTML = 30;
		}
	}
	let largePos = document.getElementById(turn + "largestraight");
	if (largePos.className !== "locked") {
		if (onesCounted >= 1 && twosCounted >= 1 && threesCounted >= 1 && foursCounted >= 1 && fivesCounted >= 1 ||
			twosCounted >= 1 && threesCounted >= 1 && foursCounted >= 1 && fivesCounted >= 1 && sixesCounted >= 1) {
			largePos.innerHTML = 40;
		}
	}
	let housePos = document.getElementById(turn + "fullhouse");
	if (housePos.className !== "locked") {
		if (counterList.includes(2) && counterList.includes(3)) {
			housePos.innerHTML = 25;
		}
	}
}

function checkUncheck(id) {
	if (isDiceRolled) {
		if (document.getElementById("die" + id).className === "checked") {
			document.getElementById("die" + id).className = "unchecked";
		} else {
			document.getElementById("die" + id).className = "checked";
		}
	}
}

function lockFaces(id) {
	let turn = getTurn()
	if (document.getElementById(turn + id).className !== "locked") {
		let facesSum = parseInt(document.getElementById(turn + id).innerHTML)
			+ parseInt(document.getElementById(turn + "facesSum").innerHTML);
		document.getElementById(turn + "facesSum").innerHTML = facesSum;
		if (facesSum >= 63) {
			document.getElementById(turn + "facesBonus").innerHTML = 35;
			document.getElementById(turn + "totalSum").innerHTML = 35
				+ parseInt(document.getElementById(turn + "totalSum").innerHTML);
		}
	}
	lock(id);
}

function lockSpecial(id) {
	lock(id);
}

function getTurn() {
	if (turnId === player1) {
		return  "p1";
	} else {
		return  "p2";
	}
}

function changeTurn() {
	if (turnId === playerId) {
		turnId = opponentId;
	} else {
		turnId = playerId;
	}
}

function updateTurn() {
	if (turnId === player1) {
		document.getElementById("turnId").innerHTML = document
			.getElementById("player" + 1).innerHTML + "'s turn!";
	} else {
		document.getElementById("turnId").innerHTML = document
			.getElementById("player" + 2).innerHTML + "'s turn!";
	}
}

function lock(id) {
	let turn = getTurn();
	if (isDiceRolled && document.getElementById(turn + id).className !== "locked") {
		document.getElementById(turn + id).className = "locked";
		document.getElementById(turn + "totalSum").innerHTML = parseInt(document
				.getElementById(turn + id).innerHTML)
			+ parseInt(document.getElementById(turn + "totalSum").innerHTML);
		rerolls = 3;
		document.getElementById("rerolls").innerHTML = "Rolls remaining: " + rerolls.toString();
		counter++;
		if (counter === 2) {
			currentRound++;
			counter = 0;
		}
		resetDice();
		document.getElementById("currentRound").innerHTML = "Round number: " + currentRound;
		let data = {
			gId: gameId,
			lockId: id
		};
		fetch("/lockIn", {
			method: "POST",
			body: JSON.stringify(data),
			headers: {"Content-type": "application/json; charset=UTF-8"}
		}).then();
		changeTurn()
		updateTurn()
		if (turnId === playerId) {
			fetch("/newTurn", {
				method: "POST",
				body: JSON.stringify(data),
				headers: {"Content-type": "application/json; charset=UTF-8"}
			})
				.then();
			opponentLocked++;
		} else {
			myLocked++
			getOpponentMoves()
		}
		checkGame()
	}
}

function checkGame() {
	if (opponentLocked === roundCount && myLocked === roundCount) {
		endGame();
		resetDice();
	}
}

function endGame() {
	let player1Score = document.getElementById("p1totalSum").innerHTML;
	let player2Score = document.getElementById("p2totalSum").innerHTML;
	if (player1Score > player2Score) {
		document.getElementById("finalScore").innerHTML = document.getElementById("player" + 1).innerHTML + " WON!";
	} else if (player1Score < player2Score) {
		document.getElementById("finalScore").innerHTML = document.getElementById("player" + 2).innerHTML + " WON!";
	} else {
		document.getElementById("finalScore").innerHTML = "It is a tie!"
	}
	document.getElementById("information").style.display = "none";
	document.getElementById("rollButton").style.display = "none";
	document.getElementById("gameEnd").style.display = "block";
}

function resetDice() {
	let tablePos;
	let turn;
	if (turnId === player1) {
		turn = "p1";
	} else {
		turn = "p2";
	}
	isDiceRolled = false;
	for (let i = 1; i <= 5; i++) {
		document.getElementById("die" + i).className = "unchecked";
		let die = document.getElementById("die" + i);
		die.src = "images/dice_" + i + ".png";
	}
	for (let i = 0; i < facesTable.length; i++) {
		tablePos = document.getElementById(turn + facesTable[i]);
		if (tablePos.className !== "locked") {
			tablePos.innerHTML = "0";
		}
	}
	for (let i = 0; i < specialTable.length; i++) {
		tablePos = document.getElementById(turn + specialTable[i]);
		if (tablePos.className !== "locked") {
			tablePos.innerHTML = "0";
		}
	}
}

function getOpponentMoves() {
	if (turnId === opponentId) {
		let currentDice = [];
		let moves = window.setInterval(function () {
			fetch('/getDice/' + gameId)
				.then(data => {
					return data.json()
				})
				.then(res => {
					let diceSum = 0;
					if (res.dice !== "null" && res.dice.join(',') !== currentDice.join(',')) {
						currentDice = res.dice
						for (let i = 1; i <= 5; i++) {
							let die = document.getElementById("die" + i);
							let value = res.dice[i - 1];
							die.src = "images/dice_" + value + ".png";
							die.value = value
							if (res.checked !== undefined && parseInt(res.checked[i - 1]) === 0) {
								document.getElementById("die" + i).className = "unchecked";
							} else if (res.checked !== undefined) {
								document.getElementById("die" + i).className = "checked";
							}
							diceSum += parseInt(value);
						}
						rerolls--;
						document.getElementById("diceScore").innerHTML = "Dice score: " + diceSum;
						checkStatus();
						document.getElementById("rerolls").innerHTML = "Rolls remaining: " + rerolls;
					}
					if (res.lockIn !== "empty") {
						isDiceRolled = true;
						if (facesTable.includes(res.lockIn.lockId.toString())) {
							lockFaces(res.lockIn.lockId.toString())
						} else {
							lockSpecial(res.lockIn.lockId.toString())
						}
						clearInterval(moves)
					}
				})
		}, 1000);
	}
}

function returnBack() {
	location.href = "http://turing.cs.ttu.ee/~jjaaks/prax4/";
}
