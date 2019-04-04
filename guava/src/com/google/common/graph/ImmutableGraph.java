/*
 * Copyright (C) 2014 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.common.graph;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.graph.GraphConstants.Presence;
import com.google.errorprone.annotations.Immutable;

/**
 * A {@link Graph} whose elements and structural relationships will never change. Instances of this
 * class may be obtained with {@link #copyOf(Graph)}.
 *
 * <p>See the Guava User's Guide's <a
 * href="https://github.com/google/guava/wiki/GraphsExplained#immutable-implementations">discussion
 * of the {@code Immutable*} types</a> for more information on the properties and guarantees
 * provided by this class.
 *
 * @author James Sexton
 * @author Joshua O'Madadhain
 * @author Omar Darwish
 * @param <N> Node parameter type
 * @since 20.0
 */
@Beta
@Immutable(containerOf = {"N"})
public class ImmutableGraph extends ForwardingGraph {
  @SuppressWarnings("Immutable") // The backing graph must be immutable.
  private final BaseGraph backingGraph;

  ImmutableGraph(BaseGraph backingGraph) {
    this.backingGraph = backingGraph;
  }

  /** Returns an immutable copy of {@code graph}. */
  public static  ImmutableGraph copyOf(Graph graph) {
    return (graph instanceof ImmutableGraph)
        ? (ImmutableGraph) graph
        : null;
  }

  /**
   * Simply returns its argument.
   *
   * @deprecated no need to use this
   */
  @Deprecated
  public static  ImmutableGraph copyOf(ImmutableGraph graph) {
    return checkNotNull(graph);
  }

  private static  ImmutableMap<Object, GraphConnections<Object, Presence>> getNodeConnections(
      Graph graph) {
    // ImmutableMap.Builder maintains the order of the elements as inserted, so the map will have
    // whatever ordering the graph's nodes do, so ImmutableSortedMap is unnecessary even if the
    // input nodes are sorted.
    
    return null;
  }

  private static  GraphConnections<Object, Presence> connectionsOf(Graph graph, Object node) {
    Function<Object, Presence> edgeValueFn = Functions.constant(Presence.EDGE_EXISTS);
    return graph.isDirected()
        ? DirectedGraphConnections.ofImmutable(
            graph.predecessors(node), Maps.asMap(graph.successors(node), edgeValueFn))
        : UndirectedGraphConnections.ofImmutable(
            Maps.asMap(graph.adjacentNodes(node), edgeValueFn));
  }

  @Override
  protected BaseGraph delegate() {
    return backingGraph;
  }
}
