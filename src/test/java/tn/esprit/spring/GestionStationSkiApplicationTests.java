package tn.esprit.spring;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.scheduling.annotation.Scheduled;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.repositories.ISubscriptionRepository;
import tn.esprit.spring.services.SubscriptionServicesImpl;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.List;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionServicesImplTest {

	@Mock
	private ISubscriptionRepository subscriptionRepository;

	@Mock
	private ISkierRepository skierRepository;

	@InjectMocks
	private SubscriptionServicesImpl subscriptionServices;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testAddSubscription() {
		Subscription subscription = new Subscription();
		subscription.setStartDate(LocalDate.now());
		subscription.setTypeSub(TypeSubscription.ANNUAL);

		Subscription savedSubscription = new Subscription();
		savedSubscription.setEndDate(subscription.getStartDate().plusYears(1));

		when(subscriptionRepository.save(any(Subscription.class))).thenReturn(savedSubscription);

		Subscription result = subscriptionServices.addSubscription(subscription);

		assertNotNull(result);
		assertEquals(savedSubscription.getEndDate(), result.getEndDate());
		verify(subscriptionRepository, times(1)).save(any(Subscription.class));
	}

	@Test
	void testUpdateSubscription() {
		Subscription subscription = new Subscription();
		when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscription);

		Subscription result = subscriptionServices.updateSubscription(subscription);

		assertNotNull(result);
		verify(subscriptionRepository, times(1)).save(any(Subscription.class));
	}

	@Test
	void testRetrieveSubscriptionById() {
		Subscription subscription = new Subscription();
		when(subscriptionRepository.findById(anyLong())).thenReturn(Optional.of(subscription));

		Subscription result = subscriptionServices.retrieveSubscriptionById(1L);

		assertNotNull(result);
		verify(subscriptionRepository, times(1)).findById(1L);
	}

	@Test
	void testGetSubscriptionByType() {
		Set<Subscription> subscriptions = new HashSet<>();
		when(subscriptionRepository.findByTypeSubOrderByStartDateAsc(any(TypeSubscription.class))).thenReturn(subscriptions);

		Set<Subscription> result = subscriptionServices.getSubscriptionByType(TypeSubscription.ANNUAL);

		assertNotNull(result);
		verify(subscriptionRepository, times(1)).findByTypeSubOrderByStartDateAsc(TypeSubscription.ANNUAL);
	}

	@Test
	void testRetrieveSubscriptionsByDates() {
		List<Subscription> subscriptions = List.of(new Subscription());
		when(subscriptionRepository.getSubscriptionsByStartDateBetween(any(LocalDate.class), any(LocalDate.class)))
				.thenReturn(subscriptions);

		List<Subscription> result = subscriptionServices.retrieveSubscriptionsByDates(LocalDate.now(), LocalDate.now().plusDays(10));

		assertNotNull(result);
		verify(subscriptionRepository, times(1)).getSubscriptionsByStartDateBetween(any(LocalDate.class), any(LocalDate.class));
	}

	@Test
	void testRetrieveSubscriptions() {
		Subscription subscription = new Subscription();
		subscription.setNumSub(1L);
		subscription.setEndDate(LocalDate.now().plusDays(5));

		Skier skier = new Skier();
		skier.setFirstName("John");
		skier.setLastName("Doe");

		when(subscriptionRepository.findDistinctOrderByEndDateAsc()).thenReturn(List.of(subscription));
		when(skierRepository.findBySubscription(subscription)).thenReturn(skier);

		subscriptionServices.retrieveSubscriptions();

		verify(subscriptionRepository, times(1)).findDistinctOrderByEndDateAsc();
		verify(skierRepository, times(1)).findBySubscription(subscription);
	}

	@Test
	void testShowMonthlyRecurringRevenue() {
		when(subscriptionRepository.recurringRevenueByTypeSubEquals(TypeSubscription.MONTHLY)).thenReturn(100.0f);
		when(subscriptionRepository.recurringRevenueByTypeSubEquals(TypeSubscription.SEMESTRIEL)).thenReturn(600.0f);
		when(subscriptionRepository.recurringRevenueByTypeSubEquals(TypeSubscription.ANNUAL)).thenReturn(1200.0f);

		subscriptionServices.showMonthlyRecurringRevenue();

		verify(subscriptionRepository, times(1)).recurringRevenueByTypeSubEquals(TypeSubscription.MONTHLY);
		verify(subscriptionRepository, times(1)).recurringRevenueByTypeSubEquals(TypeSubscription.SEMESTRIEL);
		verify(subscriptionRepository, times(1)).recurringRevenueByTypeSubEquals(TypeSubscription.ANNUAL);
	}
}

