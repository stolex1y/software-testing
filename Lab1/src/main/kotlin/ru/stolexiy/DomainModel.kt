package ru.stolexiy

import java.lang.Math.*
import kotlin.math.roundToInt

class RenegadeDetectedException(
    message: String = ""
) : Exception(message)

class WalkingDeadException(
    message: String = ""
) : Exception(message)

abstract class Creature(
    private val name: String,
    private val weapon: Weapon
) {
    var health: Int = 100
        private set

    open fun shoot(target: Creature) {
        if (isDead())
            throw WalkingDeadException()
        println("$name атаковал ${target.name}, " +
                "используя ${weapon.javaClass.simpleName}.")
        weapon.shoot(target)
        if (target.javaClass == this.javaClass)
            throw RenegadeDetectedException()
    }

    fun isAlive() = health > 0
    fun isDead() = !isAlive()

    fun injure(damage: Int) {
        if (isDead()) {
            return
        }
        health -= damage
        println("$name получил урон $damage. Его здоровье $health")
        if (isDead())
            fallDown()
    }

    private fun fallDown() {
        println("$name упал и лежит обнадеживающе тихо.")
    }
}

class Human(
    name: String
) : Creature(name, Pistol())

class Alien(
    name: String
) : Creature(name, KillOZap())

abstract class Weapon(private val damage: Int) {
    open fun shoot(target: Creature) {
        target.injure(damage)
    }
}

class Pistol : Weapon(randomDamage()) {
    companion object {
        private fun randomDamage() = (random() * 10).roundToInt()
    }

    override fun shoot(target: Creature) {
        println("Произошел выстрел.")
        super.shoot(target)
    }
}

class KillOZap : Weapon(randomDamage()) {
    companion object {
        private fun randomDamage() = (random() * 10 + 10).roundToInt()
    }

    override fun shoot(target: Creature) {
        println("С грохотом ударил разряд килобац-энергии.")
        super.shoot(target)
    }
}

class Battle {
    private val aliens = mutableListOf<Alien>()
    private val humans = mutableListOf<Human>()

    fun addToAliens(a: Alien) = aliens.add(a)
    fun addToHumans(h: Human) = humans.add(h)

    fun aliensSize() = aliens.size
    fun humansSize() = humans.size

    fun start() {
        while (aliensSize() > 0 && humansSize() > 0) {
            val alien: Alien = getRandomCreature(aliens)
            val human: Human = getRandomCreature(humans)
            Thread.sleep(1000)
            if (random() >= 0.5) {
                alien.shoot(human)
                Thread.sleep(1000)
                if (human.isAlive())
                    human.shoot(alien)
            } else {
                human.shoot(alien)
                Thread.sleep(1000)
                if (alien.isAlive())
                    alien.shoot(human)
            }
            if (alien.isDead())
                aliens -= alien
            if (human.isDead())
                humans -= human
        }
    }

    private fun <T> getRandomCreature(creatures: List<T>): T =
        creatures[(random() * creatures.size).toInt()]
}

/*
fun main() {
    val alienAbbu = Alien("Abbu")
    val alienUbba = Alien("Ubba")
    val humanHenry = Human("Henry")
    val humanFord = Human("Ford")

    val battle = Battle()
    battle.addToAliens(alienAbbu)
    battle.addToAliens(alienUbba)
    battle.addToHumans(humanHenry)
    battle.addToHumans(humanFord)
    battle.start()
}
*/
