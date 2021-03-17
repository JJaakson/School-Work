package ee.taltech.iti0200.bonuscards.cards;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Comparator;
import java.util.Collections;
import java.util.stream.Collectors;

public class Store {

    private final String name;
    private Set<Person> customers = new HashSet<>();

    public Store(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Set<Person> getCustomers() {
        return customers;
    }

    public void addCustomer(Person person) {
        customers.add(person);
    }

    public void removeCustomer(Person person) {
        customers.remove(person);
    }

    /**
     * Gets customer with the highest bonus balance.
     *
     * @param cardType the bonus card type
     * @return customer with the highest bonus balance
     */
    public Optional<Person> getCustomerWithHighestBonusBalance(BonusCard.CardType cardType) {
        if (customers.size() > 0) {
            Set<BonusCard> correctCards = new HashSet<>();
            for (Person customer : customers) {
                for (BonusCard card : customer.getBonusCards()) {
                    if (card.getType().equals(cardType)) {
                        correctCards.add(card);
                    }
                }
            }
            List<BonusCard> sortedCards = correctCards.stream()
                    .sorted(Comparator.comparing(BonusCard::getBonusBalance)).collect(Collectors.toList());
            Collections.reverse(sortedCards);
            return Optional.of(sortedCards.get(0).getPerson());
        } else {
            return Optional.empty();
        }
    }

    /**
     * Gets customer with the lowest bonus balance who is younger than the specified age.
     *
     * @param cardType the bonus card type
     * @param age the age
     * @return customer
     */
    public Optional<Person> getCustomerWithLowestBonusBalanceYoungerThan(BonusCard.CardType cardType, int age) {
        if (customers.size() > 0) {
            Set<BonusCard> correctCards = new HashSet<>();
            for (Person customer : customers) {
                if (customer.getAge() < age) {
                    for (BonusCard card : customer.getBonusCards()) {
                        if (card.getType().equals(cardType)) {
                            correctCards.add(card);
                        }
                    }
                }
            }
            List<BonusCard> sortedCards = correctCards.stream()
                    .sorted(Comparator.comparing(BonusCard::getBonusBalance)).collect(Collectors.toList());
            return Optional.of(sortedCards.get(0).getPerson());
        } else {
            return Optional.empty();
        }
    }

    /**
     * Gets total bonuses.
     *
     * @param cardType the bonus card type
     * @return the total bonuses
     */
    public BigDecimal getTotalBonuses(BonusCard.CardType cardType) {
        if (customers.size() > 0) {
            BigDecimal totalBonuses = BigDecimal.ZERO;
            for (Person customer : customers) {
                for (BonusCard card : customer.getBonusCards()) {
                    if (card.getType().equals(cardType)) {
                        totalBonuses = totalBonuses.add(card.getBonusBalance());
                    }
                }
            }
            return totalBonuses;
        } else {
            return BigDecimal.ZERO;
        }
    }

    /**
     * Gets average bonus.
     *
     * @param cardType the bonus card type
     * @return the average bonus
     */
    public BigDecimal getAverageBonus(BonusCard.CardType cardType) {
        if (customers.size() > 0) {
            BigDecimal totalBonus = BigDecimal.ZERO;
            int cards = 0;
            for (Person customer : customers) {
                for (BonusCard card : customer.getBonusCards()) {
                    if (card.getType().equals(cardType)) {
                        totalBonus = totalBonus.add(card.getBonusBalance());
                        cards += 1;
                    }
                }
            }
            return totalBonus.divide(BigDecimal.valueOf(cards), RoundingMode.UNNECESSARY);
        } else {
            return BigDecimal.ZERO;
        }
    }
}
