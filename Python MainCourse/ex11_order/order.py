"""Order system."""


class OrderItem:
    """Order Item requested by a customer."""

    def __init__(self, customer: str, name: str, quantity: int, one_item_volume: int):
        """
        Constructor that creates an order item.

        :param customer: requester name.
        :param name: the name of the item.
        :param quantity: quantity that shows how many such items customer needs.
        :param one_item_volume: the volume of one item.
        """
        self.customer = customer
        self.name = name
        self.quantity = quantity
        self.one_item_volume = one_item_volume

    @property
    def total_volume(self) -> int:
        """
        Calculate and return total volume of the current order item.

        :return: Total volume (cm^3), int.
        """
        return self.quantity * self.one_item_volume


class Order:
    """Combination of order items of one customer."""

    def __init__(self, order_items: list):
        """
        Constructor that creates an order.

        :param order_items: list of order items.
        """
        self.order_items = order_items
        self.destination = None

    @property
    def total_quantity(self) -> int:
        """
        Calculate and return the sum of quantities of all items in the order.

        :return: Total quantity as int.
        """
        total = 0
        for i in self.order_items:
            total += i.quantity
        return total

    @property
    def total_volume(self) -> int:
        """
        Calculate and return the total volume of all items in the order.

        :return: Total volume (cm^3) as int.
        """
        total = 0
        for i in self.order_items:
            total += i.total_volume
        return total


class Container:
    """Container to transport orders."""

    # define constructor

    # define volume left property method

    def __init__(self, volume, orders):
        """Initialize container."""
        self.volume = volume
        self.orders = orders

    @property
    def volume_left(self):
        """Return how much volume is left in the container."""
        total = 0
        for i in self.orders:
            total += i.total_volume
        return self.volume - total


class OrderAggregator:
    """Algorithm of aggregating orders."""

    def __init__(self):
        """Initialize order aggregator."""
        self.order_items = []

    def add_item(self, item: OrderItem):
        """
        Add order item to the aggregator.

        :param item: Item to add.
        :return: None
        """
        return self.order_items.append(item)

    def aggregate_order(self, customer: str, max_items_quantity: int, max_volume: int):
        """
        Create an order for customer which contains order lines added by add_item method.

        Iterate over added orders items and add them to order if they are for given customer
        and can fit to the orderr.

        :param customer: Customer's name to create an order for.
        :param max_items_quantity: Maximum amount on items in order.
        :param max_volume: Maximum volume of order. All items volumes must not exceed this value.
        :return: Order.
        """
        items = []
        # collect items to the order here
        for i in self.order_items:
            if i.customer == customer and i.quantity <= max_items_quantity and i.total_volume <= max_volume:
                items.append(i)
                max_items_quantity -= i.quantity
                max_volume -= i.total_volume
        for i in items:
            self.order_items.remove(i)
        return Order(items)


class ContainerAggregator:
    """Algorithm to prepare containers."""

    def __init__(self, container_volume: int):
        """
        Initialize Container Aggregator.

        :param container_volume: Volume of each container created by this aggregator.
        """
        self.container_volume = container_volume
        self.not_used_orders = []

    def prepare_containers(self, orders: tuple) -> dict:
        """
        Create containers and put orders to them.

        If order cannot be put to a container, it is added to self.not_used_orders list.

        :param orders: tuple of orders.
        :return: dict where keys are destinations and values are containers to that destination with orders.
        """
        containers = {}
        for o in orders:
            is_added = False
            if o.destination not in containers.keys() and self.container_volume >= o.total_volume:
                containers[o.destination] = []
            if o.destination in containers.keys():
                for c in containers[o.destination]:
                    if o.total_volume < c.volume_left:
                        c.orders.append(o)
                        is_added = True
                        break
            if not is_added and self.container_volume >= o.total_volume:
                containers[o.destination].append(Container(self.container_volume, [o]))
            if self.container_volume < o.total_volume:
                self.not_used_orders.append(o)
        return containers


if __name__ == '__main__':
    orders_items = (
        OrderItem("Apple", "iPhone 11", 103, 10),
        OrderItem("Apple", "iPhone X", 41, 9),
        OrderItem("Tallink", "Laev", 1, 100000),  # too big
        OrderItem("Nike", "Sneakers", 244, 10),
        OrderItem("Nike", "Other ", 145, 11),
        OrderItem("Paper", "Paper", 1030, 5),
        OrderItem("Apple", "Apple TV", 12, 5),
        OrderItem("???", "___", 235, 10),
    )

    oa = OrderAggregator()
    for oi in orders_items:
        oa.add_item(oi)

    apple_orders_quantity = 103 + 41
    apple_orders_volume = 103 * 10 + 41 * 9
    apple_order = oa.aggregate_order("Apple", apple_orders_quantity, apple_orders_volume)
    apple_order.destination = "Somewhere"
    nike_order_quantity_with_buffer = 244 + 145 + 10
    nike_order_volume_with_buffer = 244 * 10 + 145 * 11 + 99
    nike_order = oa.aggregate_order("Nike", nike_order_quantity_with_buffer, nike_order_volume_with_buffer)
    nike_order.destination = "Neverland"

    volume = 244 * 10 + 145 * 11
    ca = ContainerAggregator(volume)
    containers = ca.prepare_containers((nike_order, apple_order))

    print(apple_order.total_quantity)
    print(apple_order.total_volume)
    print(apple_order.order_items)

    print(nike_order.total_quantity)
    print(nike_order.total_volume)
    print(nike_order.order_items)

    print(len(containers))
    somewhere_containers = containers['Somewhere']
    print(len(somewhere_containers))
    print(somewhere_containers[0].volume)
    print(somewhere_containers[0].orders)

    neverland_containers = containers['Neverland']
    print(len(neverland_containers))
    print(neverland_containers[0].volume)
    print(neverland_containers[0].orders)
