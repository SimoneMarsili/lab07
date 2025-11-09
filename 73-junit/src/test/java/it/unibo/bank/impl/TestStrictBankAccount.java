package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {

    private static final int ACCEPTABLE_MESSAGE_LENGTH = 10;
    private static final double TEST_MANAGEMENT_FEES = 5.1;

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", 1);
        this.bankAccount = new StrictBankAccount(mRossi, 0.0);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    void testInitialization() {
        assertEquals(0.0, this.bankAccount.getBalance());
        assertEquals(0, this.bankAccount.getTransactionsCount());
        assertEquals(this.mRossi, this.bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    void testManagementFees() {
        this.bankAccount.deposit(this.bankAccount.getAccountHolder().getUserID(), 100);
        assertEquals(this.bankAccount.getBalance(), 100);
        assertEquals(this.bankAccount.getTransactionsCount(), 1);
        this.bankAccount.chargeManagementFees(this.bankAccount.getAccountHolder().getUserID());
        assertEquals(this.bankAccount.getBalance(), 100 - TEST_MANAGEMENT_FEES);
        assertEquals(this.bankAccount.getTransactionsCount(), 0);
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    void testNegativeWithdraw() {
        try {
            this.bankAccount.withdraw(this.bankAccount.getAccountHolder().getUserID(), -1);
            Assertions.fail("Withdrawing a negative amount was possible, but should have thrown an exception");
        } catch (final IllegalArgumentException e) {
            assertEquals(0, bankAccount.getBalance());
            assertEquals(this.bankAccount.getTransactionsCount(), 0);
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
            assertTrue(e.getMessage().length() >= ACCEPTABLE_MESSAGE_LENGTH);
        }
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    void testWithdrawingTooMuch() {
        try {
            this.bankAccount.withdraw(this.bankAccount.getAccountHolder().getUserID(), 1);
            Assertions.fail("Withdrawing more money than it is in the account was possible, but should have thrown an exception");
        } catch (final IllegalArgumentException e) {
            assertEquals(0, bankAccount.getBalance());
            assertEquals(this.bankAccount.getTransactionsCount(), 0);
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
            assertTrue(e.getMessage().length() >= ACCEPTABLE_MESSAGE_LENGTH);
        }
    }
}
