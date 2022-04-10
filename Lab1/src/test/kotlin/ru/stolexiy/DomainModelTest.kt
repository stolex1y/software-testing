package ru.stolexiy

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.greaterThan
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.*

internal class DomainModelTest {

    @Test
    fun `dead creature can't shoot test`() {
        val alien1 = Alien("Alien1")
        val alien2 = Alien("Alien2")
        assertAll(
            { assert(alien1.isAlive()) },
            { assert(alien2.isAlive()) }
        )
        alien1.injure(alien1.health)
        assertAll(
            { assert(alien1.isDead()) },
            { assert(alien2.isAlive()) }
        )
        assertThrows<WalkingDeadException> { alien1.shoot(alien2) }
    }

    @Test
    fun `damage is greater than health test`() {
        val alien1 = Alien("Alien1")
        assert(alien1.isAlive())
        alien1.injure(alien1.health * 2)
        assert(alien1.isDead())
        assertEquals(0, alien1.health)
    }

    @Test
    fun `creature can't shoot its own test`() {
        val alien1 = Alien("Alien1")
        val alien2 = Alien("Alien2")
        assertThrows<RenegadeDetectedException> { alien1.shoot(alien2) }
    }

    @Test
    fun `shoot damage test`() {
        val alien = Alien("Alien")
        val human = Human("Human")
        assertAll(
            { assertEquals(100, human.health) },
            { assertEquals(100, alien.health) },
        )
        alien.shoot(human)
        assertAll(
            { assertTrue(human.health < 100) },
            { assertEquals(100, alien.health) },
        )
    }

    @Test
    fun `dead creature is dead test`() {
        val alien = Alien("Alien")
        assertAll(
            { assertFalse(alien.isDead()) },
            { assertTrue(alien.isAlive()) }
        )
        alien.injure(100)
        assertAll(
            { assertTrue(alien.isDead()) },
            { assertFalse(alien.isAlive()) }
        )
    }

    @Test
    fun `add creature to battle team test`() {
        val battle = Battle()
        val alien = Alien("Alien")
        val human = Human("Human")
        assertAll(
            { assertEquals(0, battle.aliensSize()) },
            { assertEquals(0, battle.humansSize()) }
        )
        battle.addToAliens(alien)
        battle.addToHumans(human)
        assertAll(
            { assertEquals(1, battle.aliensSize()) },
            { assertEquals(1, battle.humansSize()) }
        )
    }

    @Test
    fun `aliens' win test`() {
        val battle = Battle()
        battle.timeout = 0
        val alien = Alien("Alien")
        val alien1 = Alien("Alien1")
        val alien2 = Alien("Alien2")
        val human = Human("Human")
        battle.addToAliens(alien)
        battle.addToAliens(alien1)
        battle.addToAliens(alien2)
        battle.addToHumans(human)
        battle.start()
        assertAll(
            { assertThat(battle.humansSize(), equalTo(0)) },
            { assertThat(battle.aliensSize(), greaterThan(0)) }
        )
    }

    @Test
    fun `humans' win test`() {
        val battle = Battle()
        battle.timeout = 0
        val alien = Alien("Alien")
        val human = Human("Human")
        val human1 = Human("Human1")
        val human2 = Human("Human2")
        battle.addToAliens(alien)
        battle.addToHumans(human)
        battle.addToHumans(human1)
        battle.addToHumans(human2)
        battle.start()
        assertAll(
            { assertThat(battle.aliensSize(), equalTo(0)) },
            { assertThat(battle.humansSize(), greaterThan(0)) }
        )
    }

}