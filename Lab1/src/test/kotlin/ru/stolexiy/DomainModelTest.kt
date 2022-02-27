package ru.stolexiy

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import kotlin.test.*

internal class DomainModelTest {

    @Test
    fun `dead creature can't shoot test`() {
        val alien1 = Alien("")
        val alien2 = Alien("")
        alien1.injure(100)
        assertThrows<WalkingDeadException> { alien1.shoot(alien2) }
    }

    @Test
    fun `creature can't shoot its own test`() {
        val alien1 = Alien("")
        val alien2 = Alien("")
        assertThrows<RenegadeDetectedException> { alien1.shoot(alien2) }
    }

    @Test
    fun `shoot damage test`() {
        val alien = Alien("")
        val human = Human("")
        alien.shoot(human)
        assertAll(
            { assertTrue(human.health < 100) },
            { assertEquals(100, alien.health) },
        )
    }

    @Test
    fun `dead creature is dead test`() {
        val alien = Alien("")
        alien.injure(100)
        assertAll(
            { assertTrue(alien.isDead()) },
            { assertFalse(alien.isAlive()) }
        )
    }

    @Test
    fun `add creature to battle team test`() {
        val battle = Battle()
        val alien = Alien("")
        val human = Human("")
        battle.addToAliens(alien)
        battle.addToHumans(human)
        assertAll(
            { assertEquals(1, battle.aliensSize()) },
            { assertEquals(1, battle.humansSize()) }
        )
    }

    @Test
    fun `battle result test`() {
        val battle = Battle()
        val alien = Alien("")
        val human = Human("")
        battle.addToAliens(alien)
        battle.addToHumans(human)
        battle.start()
        assertTrue((battle.aliensSize() == 0).xor(battle.humansSize() == 0))
    }

}